package com.zx.zxutils.other.QuickAdapter;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import com.zx.zxutils.R;
import com.zx.zxutils.other.QuickAdapter.callback.ItemDragAndSwipeCallback;
import com.zx.zxutils.other.QuickAdapter.listener.IDraggableListener;
import com.zx.zxutils.other.QuickAdapter.listener.OnItemDragListener;
import com.zx.zxutils.other.QuickAdapter.listener.OnItemSwipeListener;

import java.util.Collections;

import static com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter.EMPTY_VIEW;
import static com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter.FOOTER_VIEW;
import static com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter.HEADER_VIEW;
import static com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter.LOADING_VIEW;


/**
 * <pre>
 *     @author : xyk
 *     e-mail : yaxiaoke@163.com
 *     time   : 2019/07/25
 *     desc   : 把拖拽、滑动删除的功能封装到一个类里，更加适合扩展
 *     更新记录：
 *          抽取接口，兼容新旧版本 2019.07.29 18：24
 *
 *
 *     version: 1.1
 * </pre>
 */
public class DraggableController implements IDraggableListener {

    private static final int NO_TOGGLE_VIEW = 0;
    private int mToggleViewId = NO_TOGGLE_VIEW;
    private ItemTouchHelper mItemTouchHelper;
    private boolean itemDragEnabled = false;
    private boolean itemSwipeEnabled = false;
    private OnItemDragListener mOnItemDragListener;
    private OnItemSwipeListener mOnItemSwipeListener;
    private boolean mDragOnLongPress = true;

    private View.OnTouchListener mOnToggleViewTouchListener;
    private View.OnLongClickListener mOnToggleViewLongClickListener;

    private ZXQuickAdapter mAdapter;

    public DraggableController(ZXQuickAdapter adapter) {
        mAdapter = adapter;
    }

    public void initView(ZXBaseHolder holder) {
        int viewType = holder.getItemViewType();

        if (mItemTouchHelper != null && itemDragEnabled && viewType != LOADING_VIEW && viewType != HEADER_VIEW
                && viewType != EMPTY_VIEW && viewType != FOOTER_VIEW) {
            if (hasToggleView()) {
                View toggleView = holder.getView(mToggleViewId);
                if (toggleView != null) {
                    toggleView.setTag(R.id.BaseQuickAdapter_viewholder_support, holder);
                    if (mDragOnLongPress) {
                        toggleView.setOnLongClickListener(mOnToggleViewLongClickListener);
                    } else {
                        toggleView.setOnTouchListener(mOnToggleViewTouchListener);
                    }
                }
            }
        }
    }


    /**
     * Set the toggle view's id which will trigger drag event.
     * If the toggle view id is not set, drag event will be triggered when the item is long pressed.
     *
     * @param toggleViewId the toggle view's id
     */
    public void setToggleViewId(int toggleViewId) {
        mToggleViewId = toggleViewId;
    }

    /**
     * Set the drag event should be trigger on long press.
     * Work when the toggleViewId has been set.
     *
     * @param longPress by default is true.
     */
    public void setToggleDragOnLongPress(boolean longPress) {
        mDragOnLongPress = longPress;
        if (mDragOnLongPress) {
            mOnToggleViewTouchListener = null;
            mOnToggleViewLongClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemTouchHelper != null && itemDragEnabled) {
                        mItemTouchHelper.startDrag((RecyclerView.ViewHolder) v.getTag(R.id.BaseQuickAdapter_viewholder_support));
                    }
                    return true;
                }
            };
        } else {
            mOnToggleViewTouchListener = new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN
                            && !mDragOnLongPress) {
                        if (mItemTouchHelper != null && itemDragEnabled) {
                            mItemTouchHelper.startDrag((RecyclerView.ViewHolder) v.getTag(R.id.BaseQuickAdapter_viewholder_support));
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            mOnToggleViewLongClickListener = null;
        }
    }

    /**
     * Enable drag items.
     * Use itemView as the toggleView when long pressed.
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper) {
        enableDragItem(itemTouchHelper, NO_TOGGLE_VIEW, true);
    }

    /**
     * Enable drag items. Use the specified view as toggle.
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     * @param toggleViewId    The toggle view's id.
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper, int toggleViewId) {
        enableDragItem(itemTouchHelper, toggleViewId, true);
    }

    /**
     * Enable drag items. Use the specified view as toggle.
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     * @param toggleViewId    The toggle view's id.
     * @param dragOnLongPress If true the drag event will be trigger on long press, otherwise on touch down.
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper, int toggleViewId, boolean dragOnLongPress) {
        itemDragEnabled = true;
        mItemTouchHelper = itemTouchHelper;
        setToggleViewId(toggleViewId);
        setToggleDragOnLongPress(dragOnLongPress);
    }

    /**
     * Disable drag items.
     */
    public void disableDragItem() {
        itemDragEnabled = false;
        mItemTouchHelper = null;
    }

    @Override
    public boolean isItemDraggable() {
        return itemDragEnabled;
    }

    @Override
    public boolean hasToggleView() {
        return mToggleViewId != NO_TOGGLE_VIEW;
    }

    /**
     * <p>Enable swipe items.</p>
     * You should attach {@link ItemTouchHelper} which construct with {@link ItemDragAndSwipeCallback} to the Recycler when you enable this.
     */
    public void enableSwipeItem() {
        itemSwipeEnabled = true;
    }

    public void disableSwipeItem() {
        itemSwipeEnabled = false;
    }

    @Override
    public boolean isItemSwipeEnable() {
        return itemSwipeEnabled;
    }

    /**
     * @param onItemDragListener Register a callback to be invoked when drag event happen.
     */
    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        mOnItemDragListener = onItemDragListener;
    }

    public int getViewHolderPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() - mAdapter.getHeaderLayoutCount();
    }

    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        int from = getViewHolderPosition(source);
        int to = getViewHolderPosition(target);

        if (inRange(from) && inRange(to)) {
            if (from < to) {
                for (int i = from; i < to; i++) {
                    Collections.swap(mAdapter.getData(), i, i + 1);
                }
            } else {
                for (int i = from; i > to; i--) {
                    Collections.swap(mAdapter.getData(), i, i - 1);
                }
            }
            mAdapter.notifyItemMoved(source.getAdapterPosition(), target.getAdapterPosition());
        }

        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragMoving(source, from, target, to);
        }
    }

    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragEnd(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    public void setOnItemSwipeListener(OnItemSwipeListener listener) {
        mOnItemSwipeListener = listener;
    }

    @Override
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.onItemSwipeStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    @Override
    public void onItemSwipeClear(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.clearView(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.onItemSwiped(viewHolder, getViewHolderPosition(viewHolder));
        }

        int pos = getViewHolderPosition(viewHolder);

        if (inRange(pos)) {
            mAdapter.getData().remove(pos);
            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onItemSwiping(Canvas canvas, RecyclerView.ViewHolder viewHolder, float x, float y, boolean isCurrentlyActive) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.onItemSwipeMoving(canvas, viewHolder, x, y, isCurrentlyActive);
        }
    }

    private boolean inRange(int position) {
        return position >= 0 && position < mAdapter.getData().size();
    }

}
