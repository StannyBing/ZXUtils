package com.zx.zxutils.views.SlidingLayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.zxutils.R;
import com.zx.zxutils.views.SlidingLayout.callback.DragListener;
import com.zx.zxutils.views.SlidingLayout.callback.DragStateListener;
import com.zx.zxutils.views.SlidingLayout.transform.CompositeTransformation;
import com.zx.zxutils.views.SlidingLayout.transform.ElevationTransformation;
import com.zx.zxutils.views.SlidingLayout.transform.RootTransformation;
import com.zx.zxutils.views.SlidingLayout.transform.ScaleTransformation;
import com.zx.zxutils.views.SlidingLayout.transform.YTranslationTransformation;
import com.zx.zxutils.views.SlidingLayout.util.ActionBarToggleAdapter;
import com.zx.zxutils.views.SlidingLayout.util.DrawerListenerAdapter;
import com.zx.zxutils.views.SlidingLayout.util.HiddenMenuClickConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yarolegovich on 24.03.2017.
 */

public class ZXSlidingRootNavBuilder {

    private static final float DEFAULT_END_SCALE = 0.60f;
    private static final int DEFAULT_END_ELEVATION_DP = 8;
    private static final int DEFAULT_DRAG_DIST_DP = 180;

    private Activity activity;

    private ViewGroup contentView;

    private View menuView;
    private int menuLayoutRes;

    private List<RootTransformation> transformations;

    private List<DragListener> dragListeners;

    private List<DragStateListener> dragStateListeners;

    private int dragDistance;

    private Toolbar toolbar;

    private SlideGravity gravity;

    private boolean isMenuOpened;

    private boolean isMenuLocked;

    private Bundle savedState;

    public ZXSlidingRootNavBuilder(Activity activity) {
        this.activity = activity;
        this.transformations = new ArrayList<>();
        this.dragListeners = new ArrayList<>();
        this.dragStateListeners = new ArrayList<>();
        this.gravity = SlideGravity.LEFT;
        this.dragDistance = dpToPx(DEFAULT_DRAG_DIST_DP);
    }

    public ZXSlidingRootNavBuilder withMenuView(View view) {
        menuView = view;
        return this;
    }

    public ZXSlidingRootNavBuilder withMenuLayout(@LayoutRes int layout) {
        menuLayoutRes = layout;
        return this;
    }

    public ZXSlidingRootNavBuilder withToolbarMenuToggle(Toolbar tb) {
        toolbar = tb;
        return this;
    }

    public ZXSlidingRootNavBuilder withGravity(SlideGravity g) {
        gravity = g;
        return this;
    }

    public ZXSlidingRootNavBuilder withContentView(ViewGroup cv) {
        contentView = cv;
        return this;
    }

    public ZXSlidingRootNavBuilder withMenuLocked(boolean locked) {
        isMenuLocked = locked;
        return this;
    }

    public ZXSlidingRootNavBuilder withSavedState(Bundle state) {
        savedState = state;
        return this;
    }

    public ZXSlidingRootNavBuilder withMenuOpened(boolean opened) {
        isMenuOpened = opened;
        return this;
    }

    public ZXSlidingRootNavBuilder withDragDistance(int dp) {
        return withDragDistancePx(dpToPx(dp));
    }

    public ZXSlidingRootNavBuilder withDragDistancePx(int px) {
        dragDistance = px;
        return this;
    }

    public ZXSlidingRootNavBuilder withRootViewScale(@FloatRange(from = 0.01f) float scale) {
        transformations.add(new ScaleTransformation(scale));
        return this;
    }

    public ZXSlidingRootNavBuilder withRootViewElevation(@IntRange(from = 0) int elevation) {
        return withRootViewElevationPx(dpToPx(elevation));
    }

    public ZXSlidingRootNavBuilder withRootViewElevationPx(@IntRange(from = 0) int elevation) {
        transformations.add(new ElevationTransformation(elevation));
        return this;
    }

    public ZXSlidingRootNavBuilder withRootViewYTranslation(int translation) {
        return withRootViewYTranslationPx(dpToPx(translation));
    }

    public ZXSlidingRootNavBuilder withRootViewYTranslationPx(int translation) {
        transformations.add(new YTranslationTransformation(translation));
        return this;
    }

    public ZXSlidingRootNavBuilder addRootTransformation(RootTransformation transformation) {
        transformations.add(transformation);
        return this;
    }

    public ZXSlidingRootNavBuilder addDragListener(DragListener dragListener) {
        dragListeners.add(dragListener);
        return this;
    }

    public ZXSlidingRootNavBuilder addDragStateListener(DragStateListener dragStateListener) {
        dragStateListeners.add(dragStateListener);
        return this;
    }

    public ZXSlidingRootNav build() {
        ViewGroup contentView = getContentView();

        View oldRoot = contentView.getChildAt(0);
        contentView.removeAllViews();

        final ZXSlidingRootNavLayout newRoot = createAndInitNewRoot(oldRoot);

        View menu = getMenuViewFor(newRoot);


        HiddenMenuClickConsumer clickConsumer = new HiddenMenuClickConsumer(activity);
        clickConsumer.setMenuHost(newRoot);

        newRoot.addView(menu);
        newRoot.addView(clickConsumer);
        newRoot.addView(oldRoot);

        View spaceView = new View(activity);
        spaceView.setTag("spaceView");
        spaceView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        spaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newRoot.isMenuHidden()) {
                    newRoot.closeMenu();
                }
            }
        });
        ((ViewGroup) oldRoot).addView(spaceView);
        spaceView.setVisibility(View.GONE);

        oldRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newRoot.isMenuHidden()) {
                    newRoot.closeMenu();
                }
            }
        });

        contentView.addView(newRoot);

        if (savedState == null) {
            if (isMenuOpened) {
                newRoot.openMenu(false);
            }
        }

        newRoot.setMenuLocked(isMenuLocked);

        initToolbarMenuVisibilityToggle(newRoot, menu, spaceView);
        return newRoot;
    }

    private ZXSlidingRootNavLayout createAndInitNewRoot(View oldRoot) {
        ZXSlidingRootNavLayout newRoot = new ZXSlidingRootNavLayout(activity);
        newRoot.setId(R.id.srn_root_layout);
        newRoot.setRootTransformation(createCompositeTransformation());
        newRoot.setMaxDragDistance(dragDistance);
        newRoot.setGravity(gravity);
        newRoot.setRootView(oldRoot);
        for (DragListener l : dragListeners) {
            newRoot.addDragListener(l);
        }
        for (DragStateListener l : dragStateListeners) {
            newRoot.addDragStateListener(l);
        }
        return newRoot;
    }

    private ViewGroup getContentView() {
        if (contentView == null) {
            contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        }
        if (contentView.getChildCount() != 1) {
            throw new IllegalStateException(activity.getString(R.string.srn_ex_bad_content_view));
        }
        return contentView;
    }

    private View getMenuViewFor(ZXSlidingRootNavLayout parent) {
        if (menuView == null) {
            if (menuLayoutRes == 0) {
                throw new IllegalStateException(activity.getString(R.string.srn_ex_no_menu_view));
            }
            menuView = LayoutInflater.from(activity).inflate(menuLayoutRes, parent, false);
        }
        return menuView;
    }

    private RootTransformation createCompositeTransformation() {
        if (transformations.isEmpty()) {
            return new CompositeTransformation(Arrays.asList(
                    new ScaleTransformation(DEFAULT_END_SCALE),
                    new ElevationTransformation(dpToPx(DEFAULT_END_ELEVATION_DP))));
        } else {
            return new CompositeTransformation(transformations);
        }
    }

    protected void initToolbarMenuVisibilityToggle(final ZXSlidingRootNavLayout sideNav, View drawer, View spaceView) {
        if (toolbar != null) {
            ActionBarToggleAdapter dlAdapter = new ActionBarToggleAdapter(activity);
            dlAdapter.setAdaptee(sideNav);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, dlAdapter, toolbar,
                    R.string.srn_drawer_open,
                    R.string.srn_drawer_close);
            toggle.syncState();
            DrawerListenerAdapter listenerAdapter = new DrawerListenerAdapter(toggle, drawer, spaceView);
            sideNav.addDragListener(listenerAdapter);
            sideNav.addDragStateListener(listenerAdapter);
        } else {
            DrawerListenerAdapter listenerAdapter = new DrawerListenerAdapter(null, drawer, spaceView);
            sideNav.addDragStateListener(listenerAdapter);
        }
    }

    private int dpToPx(int dp) {
        return Math.round(activity.getResources().getDisplayMetrics().density * dp);
    }

}
