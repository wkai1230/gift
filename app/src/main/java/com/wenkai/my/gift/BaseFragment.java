package com.wenkai.my.gift;

import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * Created by my on 2016/3/14.
 */
public class BaseFragment extends Fragment {

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
