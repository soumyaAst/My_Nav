package com.example.SMSManager.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.SMSManager.fragments.KnownFrag
import com.example.SMSManager.fragments.UnknownFrag
import com.example.SMSManager.util.MainActivity

class ViewPagerAdapter (fm:FragmentManager?, lifecycle: Lifecycle): FragmentStateAdapter(fm!!,lifecycle){
    override fun getItemCount(): Int {
        return 2;
    }
    companion object {
        public var fragment: Fragment? = null
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun createFragment(position: Int): Fragment {

        when(position){
            0-> fragment =
                KnownFrag(MainActivity.knownlist)
            1-> fragment =
                UnknownFrag(
                    MainActivity.unknownlist2
                )
        }
        return fragment!!
    }


}
