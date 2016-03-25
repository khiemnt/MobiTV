package com.viettel.vpmt.mobiletv.screen.videodetail;

import com.viettel.vpmt.mobiletv.base.BasePresenterImpl;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoDetailPresenterImpl extends BasePresenterImpl<VideoDetailView> implements VideoDetailPresenter
{
    public VideoDetailPresenterImpl(VideoDetailView view)
    {
        super(view);
    }

    @Override
    public void initImageSlides()
    {
//        List<ImageItem> imageItems = new ArrayList<>();
//        ImageItem imageItem0 = new ImageItem("https://s7.postimg.org/fzx5tnbjf/741133_688523984507134_237700247_o.jpg");
//        ImageItem imageItem = new ImageItem("http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        ImageItem imageItem2 = new ImageItem("http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        ImageItem imageItem3 = new ImageItem("http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        ImageItem imageItem4 = new ImageItem("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
//        imageItems.add(imageItem0);
//        imageItems.add(imageItem);
//        imageItems.add(imageItem2);
//        imageItems.add(imageItem3);
//        imageItems.add(imageItem4);
//        ImageSlideAdapter imageSlideAdapter = new ImageSlideAdapter(imageItems, mView.getViewContext());
        String url = "https://www.youtube.com/watch?v=1UnAr_wD8oQ";
        mView.doLoadVideo(url);
    }
}
