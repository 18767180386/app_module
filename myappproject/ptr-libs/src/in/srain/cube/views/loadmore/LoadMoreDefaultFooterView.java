package in.srain.cube.views.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.srain.cube.views.ptr.R;

public class LoadMoreDefaultFooterView extends RelativeLayout implements LoadMoreUIHandler {

    private TextView mTextView;
    private ImageView loadingImage;

    public LoadMoreDefaultFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }

    private void setupViews() {
         LayoutInflater.from(getContext()).inflate(R.layout.cube_views_load_more_new_footer, this);
        mTextView = (TextView) findViewById(R.id.cube_views_load_more_default_footer_text_view);
        loadingImage=(ImageView)findViewById(R.id.scalImage);

    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText(R.string.cube_ptr_refreshing);

        loadingImage.setVisibility(VISIBLE);
        final RotateAnimation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(-1);
        anim.setDuration(1000);
        loadingImage.setImageResource(R.drawable.base_waitting);
        loadingImage.startAnimation(anim);
    }

    @Override
    public void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore) {
        if (!hasMore) {
            setVisibility(VISIBLE);
            if (empty) {
                mTextView.setText("已经到底了");
                loadingImage.clearAnimation();
                loadingImage.setVisibility(INVISIBLE);

            } else {
                mTextView.setText(R.string.cube_views_load_more_loaded_no_more);
            }
            loadingImage.setVisibility(INVISIBLE);
        } else {
            setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onWaitToLoadMore(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText("加载完成，点击加载更多");
        loadingImage.setVisibility(INVISIBLE);
    }

    @Override
    public void onLoadError(LoadMoreContainer container, int errorCode, String errorMessage) {
        loadingImage.clearAnimation();
//        loadingImage.setImageResource(R.drawable.no_order_get);
        mTextView.setText("加载失败，点击重试");

    }
}
