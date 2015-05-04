package com.example.bs_36.googlesignin;

import android.graphics.Bitmap;
import android.graphics.Color;
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

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
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
    private static final int PROFILE_SETTING = 1;
    private IProfile profile2;

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
        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;

        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile));
        profile2 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2)).withIdentifier(2);
        final IProfile profile3 = new ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile3));
        final IProfile profile4 = new ProfileDrawerItem().withName("Mr. X").withEmail("mister.x.super@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile4)).withIdentifier(4);
        final IProfile profile5 = new ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));

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
                                new ProfileDrawerItem().withName(name).withEmail(email).withIcon(StartActivity.drawable),
                                profile,
                                profile2,
                                profile3,
                                profile4,
                                profile5,
                                //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                                new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
                                new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                        )
                        .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                            @Override
                            public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                                //sample usage of the onProfileChanged listener
                                //if the clicked item has the identifier 1 add a new profile ;)
                                if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                                    IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
                                    if (headerResult.getProfiles() != null) {
                                        //we know that there are 2 setting elements. set the new profile above them ;)
                                        headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                                    } else {
                                        headerResult.addProfiles(newProfile);
                                    }
                                }

                                //false if you have not consumed the event and it should close the drawer
                                return false;
                            }
                        })
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
                .withDisplayBelowToolbar(true)
                .withSliderBackgroundDrawable(getResources().getDrawable(R.drawable.body))
                .withDrawerWidthPx(width)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_candidates).withIcon(FontAwesome.Icon.faw_male).withIdentifier(0),
                        new SectionDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_polling_stations).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new SectionDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_map).withIcon(FontAwesome.Icon.faw_globe).withIdentifier(2),
                        new SectionDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_section_nearest).withIcon(FontAwesome.Icon.faw_map_marker).withIdentifier(3),
                        new SectionDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_share).withIcon(FontAwesome.Icon.faw_share_alt).withIdentifier(4),
                        new SectionDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_info).withIcon(FontAwesome.Icon.faw_info_circle).withIdentifier(5),
                        new SectionDrawerItem(),
                        new CustomPrimaryDrawerItem().withBackgroundRes(R.color.accent).withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(6),
                        new SectionDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withSelectedIconColor(Color.RED).withTintSelectedIcon(true).withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Bullhorn").withIdentifier(7)
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
                            if (fragment != null) {
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
