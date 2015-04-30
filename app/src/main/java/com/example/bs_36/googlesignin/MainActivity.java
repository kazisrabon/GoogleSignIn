package com.example.bs_36.googlesignin;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.io.InputStream;
import java.net.URI;


public class MainActivity extends ActionBarActivity {

    private Drawer.Result result;
    private AccountHeader.Result headerResult;
    Bitmap bitmap;
    InputStream inputStream;
    String name, email;
    private URI uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmap = (Bitmap) getIntent().getParcelableExtra("bitmap");
        inputStream = (InputStream) getIntent().getParcelableExtra("inputStream");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        uri = URI.create(getIntent().getStringExtra("uri"));
//        Log.e("URI", uri+"");
        Toast.makeText(MainActivity.this, uri+"", Toast.LENGTH_SHORT).show();

        initDrawer();

    }

    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
        profileDrawerItem.setEmail(email);
        profileDrawerItem.setName(name);
        profileDrawerItem.setIcon(String.valueOf(uri));
        ImageView imageView = (ImageView)findViewById(R.id.header);
        imageView.setImageBitmap(bitmap);

//        if(inputStream != null){
//
//            headerResult = new AccountHeader()
//                    .withActivity(this)
//                    .withHeaderBackground(R.drawable.header)
//                    .addProfiles(profileDrawerItem)
//                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
//                        @Override
//                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
//                            return false;
//                        }
//                    })
//                    .build();
//        }else
            {
                headerResult = new AccountHeader()
                        .withActivity(this)
                        .addProfiles(
                                new ProfileDrawerItem().withName(name).withEmail(email).withIcon(getResources().getDrawable(R.drawable.profile))
                        )
                        .build();
            }

        // Handle Toolbar
        result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.header)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_candidates).withIcon(FontAwesome.Icon.faw_male).withIdentifier(0),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_polling_stations).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_map).withIcon(FontAwesome.Icon.faw_globe).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_section_nearest).withIcon(FontAwesome.Icon.faw_map_marker).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_share).withIcon(FontAwesome.Icon.faw_share_alt).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_info).withIcon(FontAwesome.Icon.faw_info_circle).withIdentifier(5)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
                            Fragment fragment = null;
//                            if (drawerItem.getIdentifier() == 0) {
//                                fragment = new Fragment_1();
//                            }
//                              else if (drawerItem.getIdentifier() == 1) {
//                                startActivity(new Intent(MainActivity.this, PollingStationActivity.class));
//                            } else if (drawerItem.getIdentifier() == 2) {
//                                startActivity(new Intent(MainActivity.this, Map.class));
//                            } else if (drawerItem.getIdentifier() == 3) {
//                                startActivity(new Intent(MainActivity.this, NearestVenue.class));
//                            } else if (drawerItem.getIdentifier() == 4) {
//                                startActivity(new Intent(MainActivity.this, ShareActivity.class));
//                            } else if (drawerItem.getIdentifier() == 5) {
//                                startActivity(new Intent(MainActivity.this, Info1.class));
//                            }
                            if(fragment != null){
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, fragment)
                                        .commit();
                            }
                        }
                    }
                })
                .withSelectedItem(0)
                .build();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
