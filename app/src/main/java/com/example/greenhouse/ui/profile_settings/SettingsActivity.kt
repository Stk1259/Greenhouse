package com.example.greenhouse.ui.profile_settings


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.core.view.isVisible
import com.example.greenhouse.R
import com.example.greenhouse.data.entity.EntityProfile
import com.example.greenhouse.ui.base.BaseActivity
import com.example.greenhouse.ui.profile.ProfileActivity
import com.example.greenhouse.utils.ZodiacSign
import moxy.ktx.moxyPresenter
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class SettingsActivity : BaseActivity(), SettingView {

    companion object {
        private const val PICK_IMAGE_REQUEST = 110
        private const val ID = "id"
        fun createIntent(context: Context, userId: Int): Intent {
            return Intent(context, SettingsActivity::class.java).putExtra(ID, userId)
        }
    }

    private val settingsPresenter by moxyPresenter {
        SettingsPresenter(
            this, intent.getIntExtra(ID, 0)
        )
    }
    private val myFormat = "dd/MM/yyyy"
    private val dateFormat = SimpleDateFormat(myFormat, Locale.US)
    private val myCalendar = java.util.Calendar.getInstance()
    private lateinit var birthdayEditText: EditText
    private lateinit var avatar: ImageView
    private lateinit var userName: TextView
    private lateinit var phone: TextView
    private lateinit var zodiacSign: TextView
    private lateinit var name: EditText
    private lateinit var city: EditText
    private lateinit var about: EditText
    private lateinit var linearLayout: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var birthday: String
    private lateinit var zodiac: String
    private lateinit var backIcon: ImageView
    private lateinit var saveIcon: ImageView
    private  var base64Image: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        settingsPresenter.getProfileData()
        linearLayout = findViewById(R.id.settings_linear)
        avatar = findViewById(R.id.avatar_settings)
        userName = findViewById(R.id.username_settings)
        phone = findViewById(R.id.phone_settings)
        zodiacSign = findViewById(R.id.zodiac_sign_settings)
        name = findViewById(R.id.name_edit)
        city = findViewById(R.id.city_edit)
        about = findViewById(R.id.about_edit)
        progressBar = findViewById(R.id.progressBar_settings)
        birthdayEditText = findViewById(R.id.birthday_edit)
        backIcon = findViewById(R.id.back_icon_settings)
        saveIcon = findViewById(R.id.save_icon)
        backIcon.setOnClickListener {
            startActivity(
                ProfileActivity.createIntent(
                    this,
                    intent.getIntExtra(ID, 0)
                )
            )
        }
        saveIcon.setOnClickListener { onSaveIconClicked() }
        avatar.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.data != null) {
                val uri: Uri? = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    avatar.setImageBitmap(bitmap)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                    val imageBytes = byteArrayOutputStream.toByteArray()
                    base64Image =
                        android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun showLoading(loading: Boolean) {
        progressBar.isVisible = loading
        avatar.isVisible = !loading
        linearLayout.isVisible = !loading
    }

    override fun showProfileData(profileData: EntityProfile) {
        phone.text = profileData.phone
        phone.setTextAppearance(R.style.TextProfileFull)
        userName.text = profileData.username
        userName.setTextAppearance(R.style.TextProfileFull)
        if (profileData.name?.isNotEmpty() == true) {
            name.setText(profileData.name)
            name.setTextAppearance(R.style.TextProfileFull)
        }
        if (profileData.city?.isNotEmpty() == true) {
            city.setText(profileData.city)
            city.setTextAppearance(R.style.TextProfileFull)
        }
        if (profileData.avatar?.isNotEmpty() == true) {
            val imageBytes =
                android.util.Base64.decode(profileData.avatar, android.util.Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            avatar.setImageBitmap(decodedImage)
        }
        if (profileData.birthday?.isNotEmpty() == true) {
            birthdayEditText.setText(profileData.birthday)
            birthdayEditText.setTextAppearance(R.style.TextProfileFull)
            zodiacSign.isVisible = true
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = dateFormat.parse(profileData.birthday)
            val calendar = java.util.Calendar.getInstance()
            if (date != null) {
                calendar.time = date
            }
            val zodiacUtil = ZodiacSign
            val zodiac = zodiacUtil.getSign(calendar)
            zodiacSign.text = zodiac
        }
        if (profileData.status?.isNotEmpty() == true) {
            about.setText(profileData.status)
            about.setTextAppearance(R.style.TextProfileFull)
        }

        val date = OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
            updateZodiacSign()
        }
        birthdayEditText.setOnClickListener {
            DatePickerDialog(
                this,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateLabel() {
        birthday = dateFormat.format(myCalendar.time)
        birthdayEditText.setText(birthday)
    }

    private fun updateZodiacSign() {
        val zodiacUtil = ZodiacSign
        zodiac = zodiacUtil.getSign(myCalendar)
        zodiacSign.text = zodiac
        zodiacSign.isVisible = true
        zodiacSign.setTextAppearance(R.style.TextProfileFull)
    }


    private fun onSaveIconClicked() {
        settingsPresenter.setProfile(
            intent.getIntExtra(ID, 0),
            name.text.toString(),
            city.text.toString(),
            birthdayEditText.text.toString(),
            about.text.toString(),
            base64Image,
            "where?",
            userName.text.toString()
        )
    }
}