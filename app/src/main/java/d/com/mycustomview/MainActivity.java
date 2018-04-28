package d.com.mycustomview;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    CircularImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (CircularImageView) findViewById(R.id.one);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.images));
        imageView.setBorderWidth(20);
        imageView.setBorderColor(ContextCompat.getColor(this, R.color.colorAccent));
    }
}
