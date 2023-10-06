package hu.bme.aut.android.workplaceapp.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfilePagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount() = NUM_PAGES

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> MainProfileFragment()
        1 -> DetailsProfileFragment()
        else -> MainProfileFragment()
    }

    companion object{
        const val NUM_PAGES = 2
    }
}