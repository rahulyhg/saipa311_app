package key_team.com.saipa311.Sale_services;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import key_team.com.saipa311.R;
import key_team.com.saipa311.Sale_services.Deposite;
import key_team.com.saipa311.Sale_services.Exchange;
import key_team.com.saipa311.Sale_services.NewCars;
import key_team.com.saipa311.Sale_services.OldCars;
import key_team.com.saipa311.Transformer.FadePageTransformer;

/**
 * Created by ammorteza on 11/16/17.
 */
public class SaleServicesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sale_services_fragment_layout, container, false);
        initTabLayout(view);
        return view;
    }


    public void initTabLayout(View view)
    {
        SaleServicesPagerAdapter adapter = new SaleServicesPagerAdapter(getActivity().getSupportFragmentManager() , 5);
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.saleServicesViewpager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.saleServicesTabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class SaleServicesPagerAdapter extends FragmentStatePagerAdapter {
        //integer to count number of tabs
        int tabCount;
        //Constructor to the class
        public SaleServicesPagerAdapter(FragmentManager fm, int tabCount) {
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
                    NewCars tab1 = new NewCars();
                    return tab1;
                case 1:
                    OldCars tab2 = new OldCars();
                    return tab2;
                case 2:
                    Deposite tab3 = new Deposite();
                    return tab3;
                case 3:
                    Exchange tab4 = new Exchange();
                    return tab4;
                case 4:
                    Junk tab5 = new Junk();
                    return tab5;
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
                case 0: return "خودروی نو";
                case 1: return "خودروی دست دوم";
                case 2: return "سرمایه گذاری";
                case 3: return "تهاتر";
                case 4: return "خودروی فرسوده";
            }
            return "";
        }
    }
}
