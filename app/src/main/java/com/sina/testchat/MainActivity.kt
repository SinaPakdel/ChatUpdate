package com.sina.testchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.sina.testchat.databinding.ActivityMainBinding
import com.sina.testchat.db.Message
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MainAdapter()
        binding.recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.newMessageReceived.collect { newMessageReceived ->
                if (newMessageReceived) {
                    val newMessages: List<Message> = viewModel.messageList.value.takeLast(1)
                    if (newMessages.isNotEmpty()) {
                        adapter.submitList(adapter.currentList + newMessages) {
                            binding.recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
                        }
                    }
                }
            }
        }
    }
}