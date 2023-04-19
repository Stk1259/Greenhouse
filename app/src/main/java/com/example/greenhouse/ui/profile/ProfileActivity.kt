package com.example.greenhouse.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.example.greenhouse.R
import com.example.greenhouse.data.entity.EntityProfile
import com.example.greenhouse.ui.base.BaseActivity
import com.example.greenhouse.ui.chats.ChatActivity
import com.example.greenhouse.ui.profile_settings.SettingsActivity
import com.example.greenhouse.utils.ZodiacSign
import moxy.ktx.moxyPresenter
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : BaseActivity(), ProfileView {

    companion object {
        private const val ID = "id"
        fun createIntent(context: Context, userId: Int): Intent {
            return Intent(context, ProfileActivity::class.java)
                .putExtra(ID, userId)
        }
    }

    private val profilePresenter by moxyPresenter {
        ProfilePresenter(
            this,
            intent.getIntExtra(ID, 0)
        )
    }
    private lateinit var toolbar: Toolbar
    private lateinit var avatar: ImageView
    private lateinit var linearLayout: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var usernameTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var birthdayTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var zodiacSignTextView: TextView
    private lateinit var aboutTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("activity", "started")
        setContentView(R.layout.activity_profile)
        avatar = findViewById(R.id.avatar)
        linearLayout = findViewById(R.id.profile_linear)
        toolbar = findViewById(R.id.toolbar)
        progressBar = findViewById(R.id.progressBar)
        usernameTextView = findViewById(R.id.username)
        phoneTextView = findViewById(R.id.phone)
        nameTextView = findViewById(R.id.name)
        birthdayTextView = findViewById(R.id.birthday)
        cityTextView= findViewById(R.id.city)
        zodiacSignTextView = findViewById(R.id.zodiac_sign)
        aboutTextView= findViewById(R.id.about)
        profilePresenter.getProfileData()

        val settingIcon = findViewById<ImageView>(R.id.settings_icon)
        settingIcon.setOnClickListener{
            startActivity(SettingsActivity.createIntent(this, intent.getIntExtra(ID, 0)))
        }
        val backIcon = findViewById<ImageView>(R.id.back_icon_settings)
        backIcon.setOnClickListener{
            startActivity(ChatActivity.createIntent(this, intent.getIntExtra(ID, 0)))
        }
        val refreshIcon = findViewById<ImageView>(R.id.refresh_icon)
        refreshIcon.setOnClickListener{
            profilePresenter.updateData()
        }
    }

    override fun onResume() {
        super.onResume()
        profilePresenter.getProfileData()
    }

    override fun showLoading(loading: Boolean) {
        progressBar.isVisible = loading
        avatar.isVisible = !loading
        linearLayout.isVisible = !loading
    }

    override fun showProfileData(profileData: EntityProfile) {
        if (profileData.phone?.isNotEmpty() == true){
            phoneTextView.text = profileData.phone
            phoneTextView.setTextAppearance(R.style.TextProfileFull)
        }
        if (profileData.username?.isNotEmpty() == true){
            usernameTextView.text = profileData.username
            usernameTextView.setTextAppearance(R.style.TextProfileFull)
        }
        if (profileData.name?.isNotEmpty() == true){
            nameTextView.text = profileData.name
            nameTextView.setTextAppearance(R.style.TextProfileFull)
        }
        if (profileData.city?.isNotEmpty() == true){
            cityTextView.text = profileData.city
            cityTextView.setTextAppearance(R.style.TextProfileFull)
        }
        if (profileData.avatar?.isNotEmpty() == true) {
            val imageBytes = android.util.Base64.decode(profileData.avatar, android.util.Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            avatar.setImageBitmap(decodedImage)
        }
        if (profileData.birthday?.isNotEmpty() == true){
            birthdayTextView.text = profileData.birthday
            birthdayTextView.setTextAppearance(R.style.TextProfileFull)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = dateFormat.parse(profileData.birthday)
            val calendar = Calendar.getInstance()
            if (date != null) {
                calendar.time = date
            }
            val zodiacUtil = ZodiacSign
            val zodiac = zodiacUtil.getSign(calendar)
            zodiacSignTextView.text = zodiac
            zodiacSignTextView.setTextAppearance(R.style.TextProfileFull)

        }
        if (profileData.status?.isNotEmpty() == true){
            aboutTextView.text = profileData.status
            aboutTextView.setTextAppearance(R.style.TextProfileFull)
        }
    }
}