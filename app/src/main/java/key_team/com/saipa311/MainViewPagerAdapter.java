package key_team.com.saipa311;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import key_team.com.saipa311.AfterSale_services.AfterSaleServicesFragment;
import key_team.com.saipa311.Customer_services.CustomerServicesFragment;
import key_team.com.saipa311.Options.OptionsFragment;
import key_team.com.saipa311.Sale_services.SaleServicesFragment;

/**
 * Created by ammorteza on 11/16/17.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public MainViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                SaleServicesFragment tab1 = new SaleServicesFragment();
                return tab1;
            case 1:
                AfterSaleServicesFragment tab2 = new AfterSaleServicesFragment();
                return tab2;
            case 2:
                CustomerServicesFragment tab3 = new CustomerServicesFragment();
                return tab3;
            case 3:
                OptionsFragment tab4 = new OptionsFragment();
                return tab4;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
