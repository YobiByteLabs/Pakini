package mx.unam.battlehack.paquini.adaptador;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mx.unam.battlehack.paquini.R;

/**
 * Created by jagspage2013 on 28/09/14.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return ImageFragment.newInstanceOf(i);
    }

    @Override
    public int getCount() {
        return 3;
    }

    public static class ImageFragment extends Fragment{

        public static Fragment newInstanceOf(int i){
            ImageFragment image = new ImageFragment();
            Bundle args = new Bundle();
            args.putInt("position_", i);
            image.setArguments(args);
            return image;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.frag_image,null);
            int i = this.getArguments().getInt("position_");
            ImageView image = (ImageView)v.findViewById(R.id.image_frag);
            switch(i){
                case 0:
                    image.setImageResource(R.drawable.cruzroja);

                    break;
                case 1:
                    image.setImageResource(R.drawable.ymca);

                    break;
                case 2:
                    image.setImageResource(R.drawable.proninos);

                    break;

            }
            return v;
        }
    }
}
