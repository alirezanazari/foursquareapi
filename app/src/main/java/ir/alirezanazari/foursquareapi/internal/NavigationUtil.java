package ir.alirezanazari.foursquareapi.internal;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ir.alirezanazari.foursquareapi.R;


public class NavigationUtil {

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private boolean addToBackStack = true;
    private boolean replace = true;
    private boolean stateLoss;
    private boolean hasCustomAnimation;
    private boolean immediateRemove;
    private boolean isPlayAnimation = true;
    private String tag;
    private int enter;
    private int exit;
    private int popEnter;
    private int popExit;

    public NavigationUtil(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public NavigationUtil(FragmentManager fragmentManager, Fragment fragment) {
        this.fragmentManager = fragmentManager;
        this.fragment = fragment;
    }

    public boolean isFragmentVisible(String fragmentTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        return fragment != null && fragment.isVisible();
    }

    public NavigationUtil setFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public NavigationUtil setAddToBackStack(boolean addToBackStack) {
        this.addToBackStack = addToBackStack;
        return this;
    }

    public NavigationUtil setReplace(boolean replace) {
        this.replace = replace;
        return this;
    }

    public NavigationUtil setStateLoss(boolean stateLoss) {
        this.stateLoss = stateLoss;
        return this;
    }

    public NavigationUtil setImmediateRemove(boolean immediateRemove) {
        this.immediateRemove = immediateRemove;
        return this;
    }

    public NavigationUtil setPlayAnimation(boolean isPlayAnimation) {
        this.isPlayAnimation = isPlayAnimation;
        return this;
    }

    public NavigationUtil setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public NavigationUtil setAnimation(int enter, int exit, int popEnter, int popExit) {
        hasCustomAnimation = true;
        this.enter = enter;
        this.exit = exit;
        this.popEnter = popEnter;
        this.popExit = popExit;
        return this;
    }

    public void load(boolean checkStack) {
        if (fragment == null) {
            return;
        }

        if (fragmentManager == null) {
            return;
        }

        if (checkStack && fragmentManager.getBackStackEntryCount() > 1) {
            if ((fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName().equalsIgnoreCase(fragment.getClass().getName()))) {
                return;
            }
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (tag == null) {
            tag = fragment.getClass().getName();
        }

        if (isPlayAnimation) {
            if (hasCustomAnimation) {
                fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_exit_in_right, R.anim.slide_exit_out_left);
            }
        }

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }

        if (replace) {
            fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, tag);
        }

        try {
            if (stateLoss) {
                fragmentTransaction.commitAllowingStateLoss();
            } else {
                fragmentTransaction.commit();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        load(true);
    }

    public void remove() {
        try {
            if (fragment == null) {
                return;
            }
            fragmentManager.beginTransaction().remove(fragment).commit();


        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void removeAll() {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            Log.wtf(this.getClass().getName(), "fragment: " + fragmentManager.getBackStackEntryAt(i).getName());
            fragmentManager.popBackStack();
        }
    }

    public Fragment getCurrentFragment(String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

    public void popBackStack() {
        fragmentManager.popBackStack();
    }

    public void popBackStack(int count) {
        for (int i = 0; i < count; i++) {
            fragmentManager.popBackStackImmediate();
        }
    }
}