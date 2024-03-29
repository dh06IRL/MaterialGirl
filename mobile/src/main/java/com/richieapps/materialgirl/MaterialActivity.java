package com.richieapps.materialgirl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toolbar;


public class MaterialActivity extends Activity {
    View fab;
    ImageView cardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        cardImage = (ImageView) findViewById(R.id.cardImage);
        fab = findViewById(R.id.fab);
        int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        Outline outline = new Outline();
        outline.setOval(0, 0, size, size);
        fab.setOutline(outline);
        final Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("ToolBar Title");
        toolbar.setSubtitle("A Subtitle");
        toolbar.inflateMenu(R.menu.incard_toolbar_menu);
        Palette.generateAsync(((BitmapDrawable) cardImage.getDrawable()).getBitmap(), new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                toolbar.setBackgroundColor(palette.getLightVibrantColor().getRgb());
            }
        });
        ((FrameLayout) findViewById(R.id.toolbar_frame)).addView(toolbar);
        cardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MaterialActivity.this, cardImage, "cardImage");
                Intent intent = new Intent(MaterialActivity.this, DetailActivity.class);
                startActivity(intent, options.toBundle());
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.ic_action_remove) {
                    Intent intent = new Intent(MaterialActivity.this, RecyclerViewActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCircularReveal(view);
            }
        });
    }

    private void doCircularReveal(View view) {
        final View toReveal = findViewById(R.id.hiddenText);
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        float radius = Math.max(toReveal.getWidth(), toReveal.getHeight()) * 2.0f;
        if (toReveal.getVisibility() == View.INVISIBLE) {
            toReveal.setVisibility(View.VISIBLE);
            ViewAnimationUtils.createCircularReveal(toReveal, cx, cy, 0, radius).start();
        } else {
            ValueAnimator reveal = ViewAnimationUtils.createCircularReveal(
                    toReveal, cx, cy, radius, 0);
            reveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    toReveal.setVisibility(View.INVISIBLE);
                }
            });
            reveal.start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.material, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
