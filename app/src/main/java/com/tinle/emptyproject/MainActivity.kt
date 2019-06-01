package com.tinle.emptyproject

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import com.tinle.emptyproject.vm.MainViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.fantasticsoft.gposlinklib.PoslinkActivity
import com.fantasticsoft.gposlinklib.PostLinkHandler
import com.tinle.emptyproject.R.id.*
import com.tinle.emptyproject.core.PreferenceStore
import com.tinle.emptyproject.services.MusicService
import com.tinle.emptyproject.view.*


class MainActivity : PoslinkActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var prefStore:PreferenceStore

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    @Inject
    lateinit var vmFactory:ViewModelProvider.Factory
    lateinit var vieModel:MainViewModel

    private lateinit var mDrawerLayout: DrawerLayout
    lateinit var dialog: PasscodeDialog
    private  var selectedMenu:Int  = 0

    val clickListner = DialogInterface.OnClickListener { _, i ->
        if(dialog != null) {
            if(dialog.isValidPasscode()) {
                when(selectedMenu) {
                    nave_manage_checkin->{
                        switchFrag(ManageRewardsFragment())
                    }
                    nav_settings->{
                        switchFrag(SettingsFragment())
                    }
                    nav_manage_transaction->{
                        switchFrag(ManageTransactionFragment())
                    }
                }
            }
        }
    }

    fun getPosHandler():PostLinkHandler{
        return handler
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        vieModel = ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
        //postlinkHandler = PostLinkHandler(this)

        mDrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.main_nav_menu)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            //menuItem.isChecked = true
            // close drawer when item is tapped
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            when(menuItem.itemId){
                nav_settings ->{
                    selectedMenu = menuItem.itemId
                    showPasswordDialog()
                }

                nave_manage_checkin -> {
                    selectedMenu = menuItem.itemId
                    switchFrag(ManageCheckinsFragment())
                }

                nav_manage_transaction ->{
                    switchFrag(ManageTransactionFragment())
                }

                nav_payment->{
                    switchFrag(PaymentFragment())
                }
            }
            mDrawerLayout.closeDrawers()
            true
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_hamburger)
        }
        switchFrag(CheckinFragment())

        //calling init credit card payment method
        //handler.Init();
        handler.SaveCommSettings(prefStore.getCommIP(), "HTTP", "")
    }


    private fun showPasswordDialog() {
        val fm: FragmentManager = supportFragmentManager

        val fragment:BaseFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as BaseFragment
        //fragment.showPasswordDialog()
        switchFrag(SettingsFragment())
        /*
        dialog  = PasscodeDialog.newInstance("Some Title", clickListner)
        dialog.show(fm, "fragment_edit_name")  */
    }

    private fun switchFrag(fragment:BaseFragment) {
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.fragment_container, fragment)
        trans.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MusicService::class.java)

        // Below code will invoke serviceConnection's onServiceConnected method.
        //bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(fragment is SettingsFragment
                || fragment is SignUpFragment || fragment is ManageCheckinsFragment ||
                        fragment is ManageTransactionFragment
        ) {
            switchFrag(CheckinFragment())
        }
    }
}
