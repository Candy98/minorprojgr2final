package com.example.finalproj_minor_gr2.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.finalproj_minor_gr2.student.ProfileFragment;
import com.example.finalproj_minor_gr2.student.SavedCollegeFragment;
import com.example.finalproj_minor_gr2.student.SavedSchoolFRagment;
import com.example.finalproj_minor_gr2.student.SavedTeachersFragment;
import com.example.finalproj_minor_gr2.student.SearchCollegeFragment;
import com.example.finalproj_minor_gr2.student.SearchSchoolFragment;
import com.example.finalproj_minor_gr2.student.SearchTeachersFragment;

public class TabAdapter extends FragmentStatePagerAdapter {
    int nooftabs;

    public TabAdapter(FragmentManager fm, int nooftabs) {
        super(fm);
        this.nooftabs = nooftabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            }
            case 1: {
                SearchTeachersFragment searchTeachersFragment = new SearchTeachersFragment();
                return searchTeachersFragment;
            }

            case 2: {
                SavedTeachersFragment savedTeachersFragment = new SavedTeachersFragment();
                return savedTeachersFragment;
            }
            case 3:
                SearchSchoolFragment searchSchoolFragment = new SearchSchoolFragment();
                return searchSchoolFragment;
            case 4:
                SavedSchoolFRagment savedSchoolFRagment = new SavedSchoolFRagment();
                return savedSchoolFRagment;
            case 5:
                SearchCollegeFragment searchCollegeFragment = new SearchCollegeFragment();
                return searchCollegeFragment;
            case 6:
                SavedCollegeFragment savedCollegeFragment = new SavedCollegeFragment();
                return savedCollegeFragment;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return nooftabs;
    }
}

