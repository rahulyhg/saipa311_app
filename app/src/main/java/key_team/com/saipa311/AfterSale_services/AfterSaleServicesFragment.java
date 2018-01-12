package key_team.com.saipa311.AfterSale_services;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.Deposite;
import key_team.com.saipa311.Sale_services.Exchange;
import key_team.com.saipa311.Sale_services.Junk;
import key_team.com.saipa311.Sale_services.NewCars;
import key_team.com.saipa311.Sale_services.OldCars;

/**
 * Created by ammorteza on 11/16/17.
 */

public class AfterSaleServicesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.after_sale_services_fragment_layout, container, false);
        initTabLayout(view);
        return view;
    }

    public void initTabLayout(View view)
    {
        AfterSaleServicesPagerAdapter adapter = new AfterSaleServicesPagerAdapter(getActivity().getSupportFragmentManager() , 4);
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.afterSaleServicesViewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.afterSaleServicesTabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class AfterSaleServicesPagerAdapter extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public AfterSaleServicesPagerAdapter(FragmentManager fm, int tabCount) {
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
                    Assistance tab1 = new Assistance();
                    return tab1;
                case 1:
                    Turning tab2 = new Turning();
                    return tab2;
                case 2:
                    GoldCards tab3 = new GoldCards();
                    return tab3;
                case 3:
                    RepairHistory tab4 = new RepairHistory();
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

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0: return "امداد";
                case 1: return "نوبت دهی";
                case 2: return "کارت طلایی";
                case 3: return "تاریخچه تعمیرات";
            }
            return "";
        }
    }
}