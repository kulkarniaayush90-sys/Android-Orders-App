package com.oceanx.myorders

import android.os.Bundle
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.oceanx.myorders.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentScreenTag = TAG_ORDERS
    private val orderAdapter = OrderAdapter(
        onInvoiceClick = { showToast("Invoice requested for ${it.orderId}") },
        onBookAgainClick = { showToast("Booked again: ${it.orderId}") },
        onMoreClick = { showToast("More options for ${it.orderId}") }
    )

    private val allOrders = OrderRepository.sampleOrders()
    private var selectedFilter = OrderFilter.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true

        setupRecyclerView()
        setupTabs()
        setupBottomNav()
        setupHeaderActions()
        applyFilter(OrderFilter.ALL)
        showOrdersScreen()
    }

    private fun setupRecyclerView() {
        binding.recyclerOrders.layoutManager = LinearLayoutManager(this)
        binding.recyclerOrders.adapter = orderAdapter
        binding.recyclerOrders.setHasFixedSize(true)
        binding.recyclerOrders.itemAnimator = null
    }

    private fun setupTabs() {
        val tabs = listOf(
            binding.tabAll,
            binding.tabCompleted,
            binding.tabCancelled,
            binding.tabBookedAgain
        )

        tabs.forEach { tab ->
            tab.setOnClickListener {
                val filter = when (it.id) {
                    R.id.tabAll -> OrderFilter.ALL
                    R.id.tabCompleted -> OrderFilter.COMPLETED
                    R.id.tabCancelled -> OrderFilter.CANCELLED
                    else -> OrderFilter.BOOKED_AGAIN
                }
                applyFilter(filter)
            }
        }
    }

    private fun setupBottomNav() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navHome,
                R.id.navOrders -> {
                    showOrdersScreen()
                    true
                }
                R.id.navPayments -> {
                    showPlaceholderScreen(
                        tag = TAG_PAYMENTS,
                        fragment = PlaceholderFragment.newInstance(
                            R.drawable.ic_wallet,
                            R.string.payments,
                            R.string.payments_coming_soon
                        )
                    )
                    true
                }
                R.id.navAccount -> {
                    showPlaceholderScreen(
                        tag = TAG_ACCOUNT,
                        fragment = PlaceholderFragment.newInstance(
                            R.drawable.ic_account,
                            R.string.account,
                            R.string.account_coming_soon
                        )
                    )
                    true
                }
                else -> true
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navHome,
                R.id.navOrders -> showOrdersScreen()
                R.id.navPayments -> showPlaceholderScreen(
                    tag = TAG_PAYMENTS,
                    fragment = PlaceholderFragment.newInstance(
                        R.drawable.ic_wallet,
                        R.string.payments,
                        R.string.payments_coming_soon
                    )
                )
                R.id.navAccount -> showPlaceholderScreen(
                    tag = TAG_ACCOUNT,
                    fragment = PlaceholderFragment.newInstance(
                        R.drawable.ic_account,
                        R.string.account,
                        R.string.account_coming_soon
                    )
                )
            }
        }

        binding.bottomNavigation.selectedItemId = R.id.navOrders
    }

    private fun setupHeaderActions() {
        binding.btnSearch.setOnClickListener { showToast("Search tapped") }
        binding.btnFilter.setOnClickListener { showToast("Filter tapped") }
        binding.btnSort.setOnClickListener { showToast("Sort tapped") }
        binding.btnHelp.setOnClickListener { showToast("Help tapped") }
        binding.bannerClose.setOnClickListener { binding.infoBanner.isVisible = false }
    }

    private fun applyFilter(filter: OrderFilter) {
        selectedFilter = filter
        updateTabState()
        val filtered = when (filter) {
            OrderFilter.ALL -> allOrders
            OrderFilter.COMPLETED -> allOrders.filter { it.status == OrderStatus.COMPLETED }
            OrderFilter.CANCELLED -> allOrders.filter { it.status == OrderStatus.CANCELLED }
            OrderFilter.BOOKED_AGAIN -> allOrders.filter { it.status == OrderStatus.BOOKED_AGAIN }
        }
        orderAdapter.submitList(filtered)
    }

    private fun updateTabState() {
        applyTabStyle(binding.tabAll, selectedFilter == OrderFilter.ALL)
        applyTabStyle(binding.tabCompleted, selectedFilter == OrderFilter.COMPLETED)
        applyTabStyle(binding.tabCancelled, selectedFilter == OrderFilter.CANCELLED)
        applyTabStyle(binding.tabBookedAgain, selectedFilter == OrderFilter.BOOKED_AGAIN)
    }

    private fun applyTabStyle(tab: TextView, selected: Boolean) {
        tab.isSelected = selected
        tab.setBackgroundResource(if (selected) R.drawable.bg_tab_selected else R.drawable.bg_tab_unselected)
        tab.setTextColor(ContextCompat.getColor(this, if (selected) R.color.text_primary else R.color.text_secondary))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showOrdersScreen() {
        currentScreenTag = TAG_ORDERS
        binding.recyclerOrders.isVisible = true
        binding.screenContainer.isVisible = false
    }

    private fun showPlaceholderScreen(tag: String, fragment: Fragment) {
        if (currentScreenTag == tag) {
            binding.recyclerOrders.isVisible = false
            binding.screenContainer.isVisible = true
            return
        }

        currentScreenTag = tag
        binding.recyclerOrders.isVisible = false
        binding.screenContainer.isVisible = true

        supportFragmentManager.beginTransaction()
            .replace(R.id.screenContainer, fragment, tag)
            .commit()
    }

    private companion object {
        const val TAG_ORDERS = "orders_screen"
        const val TAG_PAYMENTS = "payments_placeholder"
        const val TAG_ACCOUNT = "account_placeholder"
    }
}
