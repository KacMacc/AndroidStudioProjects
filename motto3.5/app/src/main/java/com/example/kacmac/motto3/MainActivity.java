package com.example.kacmac.motto3;

import android.os.Bundle;
import android.os.StrictMode;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


import static android.os.StrictMode.setThreadPolicy;

public class MainActivity extends AppCompatActivity {

	class Pytanie {
		int id;
		String tresc;
		boolean odp;

		public Pytanie(int num, String pyt){
			id = num;
			tresc = pyt;
			odp = false;
		}
	}

	private TextView lv;
	private TextView lv1;
	private TextView lv2;
	private RadioButton but;

	RadioGroup radioGroup1;
	RadioButton radioButton11;
	RadioButton radioButton12;

	RadioGroup radioGroup2;
	RadioButton radioButton21;
	RadioButton radioButton22;

	RadioGroup radioGroup3;
	RadioButton radioButton31;
	RadioButton radioButton32;

	ArrayList<Pytanie> pytaniaa = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

		lv = findViewById(R.id.textView);
		lv1 = findViewById(R.id.textView2);
		lv2 = findViewById(R.id.textView3);

		radioGroup1 = findViewById(R.id.radioGroup1);
		radioButton11 = findViewById(R.id.radioButton11);
		radioButton12 = findViewById(R.id.radioButton12);

		radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				pytaniaa.get(0).odp = (checkedId == R.id.radioButton11);
			}
		});

		radioGroup2 = findViewById(R.id.radioGroup2);
		radioButton21 = findViewById(R.id.radioButton21);
		radioButton22 = findViewById(R.id.radioButton22);

		radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				pytaniaa.get(1).odp = (checkedId == R.id.radioButton21);
			}
		});

		radioGroup3 = findViewById(R.id.radioGroup3);
		radioButton31 = findViewById(R.id.radioButton31);
		radioButton32 = findViewById(R.id.radioButton32);

		radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				pytaniaa.get(2).odp = (checkedId == R.id.radioButton31);
			}
		});

		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getMotto();
				radioGroup1.clearCheck();
				radioGroup2.clearCheck();
				radioGroup3.clearCheck();

			}
		});

		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sendMotto();
			}
		});
	}

	private void getMotto() {
		try {
			String address = "http://192.168.0.14/Android/cos111.php";
			HttpURLConnection http = (HttpURLConnection) new URL(address).openConnection();
			http.setRequestMethod("POST");

			InputStream is = new BufferedInputStream(http.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();

			String line;

			pytaniaa.clear();



			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String[] pytania = sb.toString().split("/");
			is.close();

			for(int i = 0; i<6; i+=2)
			{
				Pytanie p = new Pytanie(Integer.parseInt(pytania[i]), pytania[i+1]);
				pytaniaa.add(p);
			}

			lv.setText(pytaniaa.get(0).tresc);
			lv1.setText(pytaniaa.get(1).tresc);
			lv2.setText(pytaniaa.get(2).tresc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendMotto() {


		if (radioGroup1.getCheckedRadioButtonId() == -1) {
			return;
		}

		if (radioGroup2.getCheckedRadioButtonId() == -1) {
			return;
		}

		if (radioGroup3.getCheckedRadioButtonId() == -1) {
			return;
		}

		try {
			URL url = new URL("http://192.168.0.14/Android/cos1112.php");

			Pytanie p0 = pytaniaa.get(0);
			Pytanie p1 = pytaniaa.get(1);
			Pytanie p2 = pytaniaa.get(2);

			String ids = URLEncoder.encode("ids", "UTF-8")+"="+URLEncoder.encode(p0.id+";"+p1.id+";"+p2.id, "UTF-8");
			String odp = URLEncoder.encode("odp", "UTF-8")+"="+URLEncoder.encode(p0.odp+";"+p1.odp+";"+p2.odp, "UTF-8");
			String args = ids + "&" + odp;

			int duration = Toast.LENGTH_SHORT;

			//Toast.makeText(this, args, duration).show();


			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);

			byte[] out = args.getBytes(StandardCharsets.UTF_8);
			int length = out.length;

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.connect();


			try(OutputStream os = http.getOutputStream()) {
				os.write(out);
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			InputStream iss=http.getInputStream();


			byte[] b=new byte[100];
			iss.read(b);

			Toast.makeText(this, new String(b), Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}