package key_team.com.saipa311;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ammorteza on 12/1/17.
 */
public class PhotoViewer extends AppCompatActivity {
    ImageView imageView ;
    PhotoViewAttacher photoViewAttacher ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_viewer_layout);
        this.initToolbar();
        this.initZoomImageView();
    }

    private void initZoomImageView()
    {
        imageView = (ImageView)findViewById(R.id.imageView);
        Drawable drawable = getResources().getDrawable(R.drawable.terms_of_sale);
        imageView.setImageDrawable(drawable);
        photoViewAttacher = new PhotoViewAttacher(imageView);
        photoViewAttacher.update();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
