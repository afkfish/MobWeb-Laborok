package hu.bme.aut.android.weatherinfo.feature.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val NUM_PAGES: Int = 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailsMainFragment()
            1 -> DetailsMoreFragment()
            else -> DetailsMainFragment()
        }
    }

    override fun getItemCount(): Int = NUM_PAGES
}