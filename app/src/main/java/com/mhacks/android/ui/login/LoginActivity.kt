package org.mhacks.mhacks.login

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.mhacks.android.MHacksApplication
import com.mhacks.android.data.kotlin.NetworkCallback
import com.mhacks.android.data.model.Login
import com.mhacks.android.data.network.NetworkSingleton
import com.mhacks.android.ui.login.components.LoginFragment
import org.mhacks.android.R
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    private val networkSingleton by lazy {
        NetworkSingleton.newInstance(application = application as MHacksApplication)
    }

    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MHacksTheme)
        setStatusBarTransparent()
        setContentView(R.layout.activity_login)
        switchFragment(LoginFragment.instance)
    }

    fun attemptLogin(email: String,
                     password: String,
                     callback: NetworkCallback<Login>) {

        networkSingleton.getLoginVerification(
                email,
                password,
                object: NetworkCallback<Login> {
                    override fun onResponseSuccess(response: Login) {
                        callback.onResponseSuccess(response)
                    }

                    override fun onResponseFailure(error: Throwable) {
                        callback.onResponseFailure(error)
                    }
                }
        )
    }

    fun switchFragment(fragment: android.support.v4.app.Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.login_container, fragment)
                .commit()
    }

    private fun setStatusBarTransparent() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }


    companion object {
        val TAG = "LoginActivity"
    }
}
