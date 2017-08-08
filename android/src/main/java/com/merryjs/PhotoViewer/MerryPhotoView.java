package com.merryjs.PhotoViewer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
//import com.merryjs.PhotoViewer.CircleProgressBarDrawable;
import com.merryjs.PhotoViewer.MerryPhotoData;
import com.merryjs.PhotoViewer.MerryPhotoOverlay;
import com.stfalcon.frescoimageviewer.ImageViewer;

/**
 * Created by bang on 07/08/2017.
 */

public class MerryPhotoView extends View {

    private MerryPhotoOverlay overlayView;
    protected ImageViewer.Builder builder;

    public MerryPhotoData[] getData() {
        return data;
    }

    public MerryPhotoView setData(MerryPhotoData[] data) {
        this.data = data;
        return this;
    }

    protected MerryPhotoData[] data;

    public String getShareText() {
        return shareText;
    }

    public MerryPhotoView setShareText(String shareText) {
        this.shareText = shareText;
        return this;
    }

    protected String shareText;


    protected int initial;

    public int getInitial() {
        return initial;
    }

    public MerryPhotoView setInitial(int initial) {
        this.initial = initial;
        return this;
    }

    public boolean isHideStatusBar() {
        return hideStatusBar;
    }

    public MerryPhotoView setHideStatusBar(boolean hideStatusBar) {
        this.hideStatusBar = hideStatusBar;
        return this;
    }

    protected boolean hideStatusBar;


    public MerryPhotoView(Context context) {
        super(context);
    }


    protected ImageViewer.Builder init() {
        final Context context = getContext();
        overlayView = new MerryPhotoOverlay(context);
        builder = new ImageViewer.Builder(context, getData())
                .setFormatter(new ImageViewer.Formatter<MerryPhotoData>() {
                    @Override
                    public String format(MerryPhotoData o) {
                        return o.url;
                    }
                })
                .setOnDismissListener(getDismissListener());

        builder.setOverlayView(overlayView);
        builder.setImageChangeListener(getImageChangeListener());
        builder.setStartPosition(getInitial());
        builder.hideStatusBar(isHideStatusBar());
//        builder.setCustomDraweeHierarchyBuilder(progressBarDrawableBuilder());

        return builder;
    }


    private ImageViewer.OnImageChangeListener getImageChangeListener() {
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {

                MerryPhotoData merryPhotoData = getData()[position];
                String url = merryPhotoData.url;
//                default use url
                overlayView.setShareContext(url);

                overlayView.setDescription(merryPhotoData.summary);
                overlayView.setTitleText(merryPhotoData.title);

                String summaryColor = "#ffffff";
                String titleColor = "#ffffff";
                if (getShareText() != null) {
                    overlayView.setShareText(getShareText());
                }
//                if (options.titlePagerColor != null) {
//                    overlayView.setPagerTextColor(options.titlePagerColor);
//                }
//
                overlayView.setPagerText((position + 1) + " / " + getData().length);
//
                if (merryPhotoData.titleColor != null) {
                    titleColor = merryPhotoData.titleColor;
                }
                overlayView.setTitleTextColor(titleColor);
                if (merryPhotoData.summaryColor != null) {
                    summaryColor = merryPhotoData.summaryColor;
                }
                overlayView.setDescriptionTextColor(summaryColor);
//
//                if (options.shareTextColor != null) {
//                    overlayView.setShareTextColor(options.shareTextColor);
//                }
            }
        };
    }

    /**
     * on dismiss
     */
    protected void onDialogDismiss() {
        final Context context = getContext();
        if (context instanceof ReactContext) {
            ((ReactContext) context).getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "onDismiss", null);
        }
    }

    //
    private ImageViewer.OnDismissListener getDismissListener() {
        return new ImageViewer.OnDismissListener() {
            @Override
            public void onDismiss() {
                onDialogDismiss();
            }
        };
    }

//    private GenericDraweeHierarchyBuilder progressBarDrawableBuilder() {
//        return GenericDraweeHierarchyBuilder.newInstance(getResources())
//                .setProgressBarImage(
//                        new CircleProgressBarDrawable()
//                        new CircularProgressDrawable
//                                .Builder()
//                                .setInnerCircleScale(0.75f)
//                                .setRingWidth(4)
//                                .setRingColor(Color.WHITE)
//                                .setCenterColor(Color.WHITE)
//                                .create()
//
//                );
//    }
}
