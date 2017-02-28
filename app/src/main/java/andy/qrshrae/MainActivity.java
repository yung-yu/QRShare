package andy.qrshrae;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import andy.qrshrae.utils.QrcodeUtil;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
	private EditText editText;
	private Button genateButton;
	private Bitmap qrcodeBitmap;
	private QrCodeTask mQrCodeTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		if(Intent.ACTION_SEND.equals(intent.getAction())){
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setTheme(R.style.DialogActivityStyle);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.image);
		editText = (EditText) findViewById(R.id.editText);
		genateButton = (Button) findViewById(R.id.button);

		if(Intent.ACTION_SEND.equals(intent.getAction())){
			genateButton.setVisibility(View.GONE);
			editText.setVisibility(View.GONE);
			handleSend(intent);
		} else {
			genateButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String content = editText.getText().toString();
					if(!TextUtils.isEmpty(content)){
						handleSendText(content);
					}
				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(qrcodeBitmap != null && !qrcodeBitmap.isRecycled()){
			qrcodeBitmap.recycle();
		}
	}

	public void handleSend(Intent intent){
		String type = intent.getType();
		if(!TextUtils.isEmpty(type)){
			if(type.startsWith("text")){
				handleSendText(intent.getStringExtra(Intent.EXTRA_TEXT));
			}

		}
	}

	void handleSendText(String sharedText) {
		if (!TextUtils.isEmpty(sharedText)) {
			mQrCodeTask = new QrCodeTask();
			mQrCodeTask.execute(sharedText);
		}
	}

	private class QrCodeTask extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			if(bitmap != null && !bitmap.isRecycled()) {
				qrcodeBitmap = bitmap;
				imageView.setImageBitmap(qrcodeBitmap);
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Bitmap doInBackground(String... params) {

			return QrcodeUtil.getQrCodeBitmap(params[0], Color.BLACK, 1024, 1024);
		}
	}
}
