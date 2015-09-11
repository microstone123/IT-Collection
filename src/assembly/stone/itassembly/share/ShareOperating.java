package assembly.stone.itassembly.share;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Context;
import assembly.stone.itassembly.R;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ShareOperating {
	public UMSocialService mController = UMServiceFactory.getUMSocialService(ShareUtils.umeng_share);
	private Context mContext;
	private UMImage umimage;
	private UMVideo umVideo;

	public ShareOperating(Context mContext) {
		super();
		this.mContext = mContext;
		mController.getConfig().closeToast();
		addWXPlatform(mContext);
		addQQQZonePlatform(mContext);
		// 添加新浪SSO授权
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.openShare((Activity) mContext, false);
		umimage = new UMImage(mContext, R.drawable.ic_launcher);
	}

	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform(Context context) {
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(context, ShareUtils.WETCHAT_APPID, ShareUtils.WETCHAT_APPSECRET);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(context, ShareUtils.WETCHAT_APPID, ShareUtils.WETCHAT_APPSECRET);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

	/**
	 * @功能描述 : 添添加QQ平台支持
	 * @return
	 */
	public void addQQQZonePlatform(Context context) {
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context, ShareUtils.QQ_APPID, ShareUtils.QQ_KEY);
		// qqSsoHandler.setTargetUrl("http://www.umeng.com");
		qqSsoHandler.addToSocialSDK();

		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, ShareUtils.QQ_APPID,
				ShareUtils.QQ_KEY);
		qZoneSsoHandler.addToSocialSDK();
	}

	public void shareToQQ(String content, String title, String url) {
		QQShareContent shareContent = new QQShareContent();
		if (StringUtils.isNotEmpty(content)) {
			shareContent.setShareContent(content);
		}
		if (StringUtils.isNotEmpty(title)) {
			shareContent.setTitle(title + "(#IT Collection)");
		}
		if (url == null) {
			shareContent.setTargetUrl(ShareUtils.umeng_share);
		} else {
			shareContent.setTargetUrl(url);
		}
		shareContent.setShareImage(umimage);
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.QQ);
	}

	public void shareToQQ(String content, String title) {
		shareToQQ(content, title, null);
	}

	public void shareToQQWithVideo(String content, String title, String url, String urlThumb) {
		QQShareContent shareContent = new QQShareContent();
		if (StringUtils.isNotEmpty(url)) {
			umVideo = new UMVideo(url);
			if (StringUtils.isNotEmpty(title)) {
				umVideo.setTitle(title + "(#IT Collection)  \n" + content);
			}
			if (StringUtils.isNotEmpty(urlThumb)) {
				umVideo.setThumb(urlThumb);
			}
			shareContent.setShareMedia(umVideo);
		}
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.QQ);
	}

	public void shareToQQ() {
		shareToQQ(null, null, null);
	}

	public void shareToWeixin(String content, String title, String url) {
		WeiXinShareContent shareContent = new WeiXinShareContent();
		if (StringUtils.isNotEmpty(content)) {
			shareContent.setShareContent(content);
		}
		if (StringUtils.isNotEmpty(title)) {
			shareContent.setTitle(title + "(#IT Collection)");
		}
		if (url == null) {
			shareContent.setTargetUrl(url);
		} else {
			shareContent.setTargetUrl(ShareUtils.umeng_share);
		}
		shareContent.setShareImage(umimage);
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.WEIXIN);
	}

	public void shareToWeixin(String content, String title) {
		shareToWeixin(content, title, null);
	}

	public void shareToWeixinWithVideo(String content, String title, String url, String urlThumb) {
		WeiXinShareContent shareContent = new WeiXinShareContent();
		if (StringUtils.isNotEmpty(url)) {
			umVideo = new UMVideo(url);
			if (StringUtils.isNotEmpty(title)) {
				umVideo.setTitle(title + "(#IT Collection)  \n" + content);
			}
			if (StringUtils.isNotEmpty(urlThumb)) {
				umVideo.setThumb(urlThumb);
			}
			shareContent.setShareMedia(umVideo);
		}
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.WEIXIN);
	}

	public void shareToWeixin() {
		shareToWeixin(null, null, null);
	}

	public void shareToCircle(String content, String title, String url) {
		CircleShareContent shareContent = new CircleShareContent();
		if (url == null) {
			shareContent.setTargetUrl(url);
		} else {
			shareContent.setTargetUrl(ShareUtils.umeng_share);
		}
		if (StringUtils.isNotEmpty(title)) {
			String str = "(#IT Collection)" + content;
			shareContent.setTitle(str);
		}
		if (StringUtils.isNotEmpty(content)) {
			shareContent.setShareContent(content);
		}
		shareContent.setTargetUrl(url);
		shareContent.setShareImage(umimage);
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
	}

	public void shareToCircle(String content, String title) {
		shareToCircle(content, title, null);
	}

	public void shareToCircleWithVideo(String content, String title, String url, String urlThumb) {
		CircleShareContent shareContent = new CircleShareContent();
		if (StringUtils.isNotEmpty(url)) {
			umVideo = new UMVideo(url);
			if (StringUtils.isNotEmpty(title)) {
				umVideo.setTitle(title + "(#IT Collection)  \n" + content);
			}
			if (StringUtils.isNotEmpty(urlThumb)) {
				umVideo.setThumb(urlThumb);
			}
			shareContent.setShareMedia(umVideo);
		}
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
	}

	public void shareToCircle() {
		shareToCircle(null, null, null);
	}

	public void shareToQZone(String content, String title, String url) {
		QZoneShareContent shareContent = new QZoneShareContent();
		if (StringUtils.isNotEmpty(content)) {
			shareContent.setShareContent(content);
		}
		if (StringUtils.isNotEmpty(title)) {
			shareContent.setTitle(title + "(#IT Collection)\n" + content);
		}
		if (url == null) {
			shareContent.setTargetUrl(ShareUtils.umeng_share);
		} else {
			shareContent.setTargetUrl(url);
		}
		shareContent.setShareImage(umimage);
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.QZONE);
	}

	public void shareToQZone(String content, String title) {
		shareToQZone(content, title, null);
	}

	public void shareToQZoneWithVideo(String content, String title, String url, String urlThumb) {
		QZoneShareContent shareContent = new QZoneShareContent();
		if (StringUtils.isNotEmpty(url)) {
			umVideo = new UMVideo(url);
			if (StringUtils.isNotEmpty(title)) {
				umVideo.setTitle(title + "(#IT Collection)  \n" + content);
			}
			shareContent.setShareMedia(umVideo);
		}
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.QZONE);
	}

	public void shareToQZone() {
		shareToQZone(null, null, null);
	}

	public void shareToSina(String content, String title) {
		SinaShareContent shareContent = new SinaShareContent();
		if (StringUtils.isNotEmpty(content)) {
			shareContent.setShareContent(content);
		}
		if (StringUtils.isNotEmpty(title)) {
			shareContent.setTitle(title);
		}
		shareContent.setTargetUrl(ShareUtils.umeng_share);
		shareContent.setShareImage(umimage);
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.SINA);
	}

	public void shareToSina() {
		shareToSina(null, null);
	}

	public void performShare(SHARE_MEDIA platform) {
		mController.postShare(mContext, platform, new SnsPostListener() {
			@Override
			public void onStart() {
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {

			}
		});
	}

}