package 
{
	import fl.controls.Button;
	import flash.media.Camera;
	import flash.media.Video;
	import flash.text.TextFormat;
	import flash.events.MouseEvent;
	import flash.display.Sprite;
	import com.adobe.images.JPGEncoder;
	import flash.utils.ByteArray;
	import flash.display.BitmapData;
	import flash.net.FileReference;
	import flash.text.TextField;

	public class Main extends Sprite
	{
		var cam:Camera = Camera.getCamera();
		var video:Video = new Video();
		var save_btn:Button = new Button();
		var msgtf:TextField = new TextField();

		public function Main():void
		{
			// constructor code
			if (cam == null)
			{
				msgtf.text = "找不到摄像头！";
				this.addChild(msgtf);
				trace("找不到摄像头！");
				return;
			}
			video = new Video(320,270);
			video.x = 0;
			video.y = 0;
			video.attachCamera(cam);
			this.addChild(video);

			//按钮
			save_btn.width = 60;
			save_btn.x = 130;
			save_btn.y = 245;
			save_btn.label = "拍照";

			var format:TextFormat = new TextFormat();
			format.font = "宋体";
			format.size = "12";
			save_btn.setStyle("textFormat",format);

			this.addChild(save_btn);

			save_btn.addEventListener(MouseEvent.CLICK,saveJPG);
		}

		private function saveJPG(mouseEvent:MouseEvent):void
		{
			var jpgEncoder:JPGEncoder = new JPGEncoder(100);
			var bytes:ByteArray = jpgEncoder.encode(getBitmapData());
			new FileReference().save(bytes,"image.jpg");
		}

		public function getBitmapData():BitmapData
		{
			var bmp:BitmapData = new BitmapData(video.width,video.height);
			bmp.draw(video);
			return bmp;
		}

	}

}