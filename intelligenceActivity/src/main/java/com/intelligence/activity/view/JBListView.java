package com.intelligence.activity.view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.intelligence.activity.R;

public class JBListView extends ListView implements OnScrollListener {

	// 下拉因子,实现下拉时的延迟效果
	private static final float PULL_FACTOR = 0.6F;

	// 回弹时每次减少的高度
	private static final int PULL_BACK_REDUCE_STEP = 1;

	// 回弹时递减headview高度的频率, 注意以纳秒为单位
	private static final int PULL_BACK_TASK_PERIOD = 600;

	// 记录下拉的起始点
	private boolean isRecored;

	// 记录刚开始下拉时的触摸位置的Y坐标
	private int startY;

	// 第一个可见条目的索引
	private int firstItemIndex;

	// 用于实现下拉弹性效果的headView
	private View headView;

	private int currentScrollState;

	// 实现回弹效果的调度器
	private ScheduledExecutorService schedulor;

	private Context _context;

	ListOnScrollListener listener;

	private LinearLayout loadMoreView;

	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	private boolean isLoad = true; // 为了防止在加载数据过程中 拖动后进行二次加载
	private int maxNum = 10; // 进行分页的时候 为了防止已经存在的数据已经是最后的数量
	private boolean isOKLoad = true;

	public boolean isOKLoad() {
		return isOKLoad;
	}

	public void setOKLoad(boolean isOKLoad) {
		this.isOKLoad = isOKLoad;
	}

	public JBListView(Context context) {
		super(context);
		this._context = context;
		initView();
	}

	public JBListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this._context = context;
		initView();
	}

	// 实现回弹效果的handler,用于递减headview的高度并请求重绘
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			AbsListView.LayoutParams params = (LayoutParams) headView
					.getLayoutParams();

			// 递减高度
			params.height -= PULL_BACK_REDUCE_STEP;

			headView.setLayoutParams(params);

			// 重绘
			headView.invalidate();

			// 停止回弹时递减headView高度的任务
			if (params.height <= 0) {
				schedulor.shutdownNow();
			}
		}
	};

	private void initView() {
		LayoutInflater inflater = LayoutInflater.from(_context);
		loadMoreView = (LinearLayout) inflater.inflate(R.layout.data_loading,
				null);
		this.addFooterView(loadMoreView); // 设置列表底部视图
		setOnScrollListener(this);
		loadMoreView.setVisibility(View.GONE);

		// 监听滚动状态
		setOnScrollListener(this);

		// 创建PullListView的headview
		headView = new View(this.getContext());

		// 默认白色背景,可以改变颜色, 也可以设置背景图片
		headView.setBackgroundColor(Color.WHITE);

		// 默认高度为0
		headView.setLayoutParams(new AbsListView.LayoutParams(
				LayoutParams.FILL_PARENT, 0));

		this.addHeaderView(headView);
	}

	/**
	 * 覆盖onTouchEvent方法,实现下拉回弹效果
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			// 记录下拉起点状态
			if (firstItemIndex == 0) {

				isRecored = true;
				startY = (int) event.getY();

			}
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:

			if (!isRecored) {
				break;
			}

			// 以一定的频率递减headview的高度,实现平滑回弹
			schedulor = Executors.newScheduledThreadPool(1);
			schedulor.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					handler.obtainMessage().sendToTarget();

				}
			}, 0, PULL_BACK_TASK_PERIOD, TimeUnit.NANOSECONDS);

			isRecored = false;

			break;

		case MotionEvent.ACTION_MOVE:

			if (!isRecored && firstItemIndex == 0) {
				isRecored = true;
				startY = (int) event.getY();
			}

			if (!isRecored) {
				break;
			}

			int tempY = (int) event.getY();
			int moveY = tempY - startY;

			if (moveY < 0) {
				isRecored = false;
				break;
			}

			headView.setLayoutParams(new AbsListView.LayoutParams(
					LayoutParams.FILL_PARENT, (int) (moveY * PULL_FACTOR)));
			headView.invalidate();

			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 设置隐藏底部试图
	 */
	public void setFooterViewGone() {
		loadMoreView.setVisibility(View.GONE);
	}

	/**
	 * 设置打开底部试图
	 */
	public void openFooterViewGone() {
		loadMoreView.setVisibility(View.VISIBLE);
	}

	public void setListOnScollListerner(ListOnScrollListener listener) {
		this.listener = listener;
	}

	public void setLoadState(boolean loadstate) {
		this.isLoad = loadstate;
	}

	public void setLoadEnd() {
		isLoad = true;
		loadMoreView.setVisibility(View.VISIBLE);
		if (listener != null) {
			listener.onEndRead();
		}
		setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
		loadMoreView.setVisibility(View.GONE);
		this.invalidate();
	}

	public void setPageMaxNumber(int maxNumber) {
		this.maxNum = maxNumber;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.visibleItemCount = visibleItemCount;
		this.visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		this.firstItemIndex = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		this.currentScrollState = scrollState;
		if (!isOKLoad)
			return;
		int itemsLastIndex = this.getAdapter().getCount() - 2; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (lastIndex >= maxNum) {
			// Toast.makeText(_context,
			// _context.getResources().getString(R.string.list_load_tishi),
			// Toast.LENGTH_SHORT).show();
			Log.e("aa", "isOKLoad-------1");
			return;
		}

		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex && isLoad) {
			if (listener != null) {
				isLoad = false;
				listener.onStartRead();
				loadMoreView.setVisibility(View.VISIBLE);
			}
		}
	}

	public interface ListOnScrollListener {
		public void onStartRead();

		public void onEndRead();
	}

}
