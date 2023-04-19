package com.example.greenhouse.ui.chats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenhouse.R
import com.example.greenhouse.data.local.chat.Chat
import com.example.greenhouse.data.local.chat.Message
import com.example.greenhouse.ui.base.BaseActivity
import com.example.greenhouse.ui.profile.ProfileActivity
import com.google.android.material.navigation.NavigationView
import moxy.ktx.moxyPresenter

class ChatActivity : BaseActivity(), ChatView, DrawerLayout.DrawerListener {

    companion object {
        private const val ID = "id"
        fun createIntent(context: Context, userId: Int): Intent {
            return Intent(context, ChatActivity::class.java)
                .putExtra(ID, userId)
        }
    }

    private val chatPresenter by moxyPresenter { ChatPresenter() }
    private lateinit var toolbar: Toolbar
    private lateinit var chatScreen: RelativeLayout
    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var inputLayout: RelativeLayout
    private lateinit var messageEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var chatsRecyclerView: RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var menuIcon: ImageView
    private lateinit var profileIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        toolbar = findViewById(R.id.toolbar)
        chatScreen = findViewById(R.id.chat_screen)
        messagesRecyclerView = findViewById(R.id.messages_recyclerview)
        inputLayout = findViewById(R.id.input_layout)
        messageEditText = findViewById(R.id.message_edittext)
        submitButton = findViewById(R.id.submit_button)
        chatsRecyclerView = findViewById(R.id.chats_recyclerview)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        chatsRecyclerView.layoutManager = LinearLayoutManager(this)
        chatsRecyclerView.adapter = ChatsAdapter(getDummyChats(), this::onChatSelected)
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        messagesRecyclerView.adapter = MessagesAdapter(getDummyMessages())
        drawerLayout.addDrawerListener(this)
        setSupportActionBar(toolbar)
        menuIcon = findViewById(R.id.menu_icon)
        profileIcon = findViewById(R.id.profile_icon)
        menuIcon.setOnClickListener { onMenuIconClicked() }
        profileIcon.setOnClickListener {onProfileIconClicker()}
        submitButton.setOnClickListener {
            val message = messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {
                messageEditText.text.clear()
                addNewMessage(message)
            }
        }
        drawerLayout.openDrawer(GravityCompat.END)
    }

    private fun onProfileIconClicker() {
        Log.d("profile", "clicked")
        startActivity(
            ProfileActivity.createIntent(
                this,
                intent.getIntExtra(ID, 0)
            )
        )
    }

    private fun onMenuIconClicked() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.openDrawer(GravityCompat.END)
            } else {
                Toast.makeText(
                    this,
                    drawerLayout.isDrawerOpen(GravityCompat.END).toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun onChatSelected(chat: Chat) {
        toolbar.title = chat.name
        chatsRecyclerView.visibility = View.GONE
        chatScreen.visibility = View.VISIBLE
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        }
    }

    private fun addNewMessage(message: String) {
        val messagesAdapter = messagesRecyclerView.adapter as MessagesAdapter
        messagesAdapter.addMessage(Message(message, true))
        messagesRecyclerView.smoothScrollToPosition(messagesAdapter.itemCount - 1)
    }

    private fun getDummyChats(): List<Chat> {
        return listOf(
            Chat("John", "Hi there!"),
            Chat("Jane", "Hey, what's up?"),
            Chat("Bob", "Can you send me that file?"),
            Chat("Alice", "I'm running late, can we reschedule?")
        )
    }


    private fun getDummyMessages(): List<Message> {
        return listOf(
            Message("Hey John, how are you?", false),
            Message("I'm good, thanks for asking. How about you?", true),
            Message("I'm doing well too, thanks.", false)
        )
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

    override fun onDrawerOpened(drawerView: View) {
        chatsRecyclerView.visibility = View.VISIBLE
        if (navigationView.parent == null) {
            drawerLayout.addView(navigationView)
        } else {
            (navigationView.parent as ViewGroup).removeView(navigationView)
            drawerLayout.addView(navigationView)
        }
    }

    override fun onDrawerClosed(drawerView: View) {
    }

    override fun onDrawerStateChanged(newState: Int) {}
}