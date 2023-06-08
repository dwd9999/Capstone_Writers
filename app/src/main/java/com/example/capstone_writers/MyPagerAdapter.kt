package com.example.capstone_writers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.capstone_writers.Fragment.EssayFragment
import com.example.capstone_writers.Fragment.FreeStyleFragment
import com.example.capstone_writers.Fragment.HomeFragment
import com.example.capstone_writers.Fragment.LongNovelFragment
import com.example.capstone_writers.Fragment.PoemFragment
import com.example.capstone_writers.Fragment.ShortNovelFragment
import com.example.capstone_writers.Fragment.SsulFragment

class MyPagerAdapter(fm : androidx.fragment.app.FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) { //switch()문과 동일하다.
            0 -> {
                HomeFragment()
            }
            1 -> {
                ShortNovelFragment()
            }
            2 -> {
                LongNovelFragment()
            }
            3 -> {
                PoemFragment()
            }
            4 -> {
                EssayFragment()
            }
            5 -> {
                SsulFragment()
            }
            else -> {return FreeStyleFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 7 //7개니깐
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "홈"
            1 -> "단편"
            2 -> "중장편"
            3 -> "시"
            4 -> "에세이"
            5 -> "썰"
            else -> {return "자유"}
        }
    }
}