package mx.unam.battlehack.paquini.controlador;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import mx.unam.battlehack.paquini.R;
import mx.unam.battlehack.paquini.fragement.FragmentAyuda;
import mx.unam.battlehack.paquini.fragement.FragmentCupones;
import mx.unam.battlehack.paquini.fragement.MisCuponesFragment;
import mx.unam.battlehack.paquini.objetos.Constants;
import mx.unam.battlehack.paquini.util.ManejadorListas;

public class ActivityMain extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.CURRENT_F = 0;
        ManejadorListas.init(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FragmentCupones.newInstance(0))
                .commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (Constants.CURRENT_F != position) {
            switch (position) {
                case 0:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, FragmentCupones.newInstance(position + 1))
                            .commit();
                    Constants.CURRENT_F = 0;
                    break;
                case 1:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, FragmentAyuda.newInstance(position + 1))
                            .commit();
                    Constants.CURRENT_F = 1;
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, MisCuponesFragment.newInstance(position + 1))
                            .commit();
                    Constants.CURRENT_F = 2;
                    break;
                default:
                    break;
            }

        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.activity_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
