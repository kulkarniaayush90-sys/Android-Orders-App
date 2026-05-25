package com.oceanx.myorders

data class OrderItem(
    val orderId: String,
    val title: String,
    val meta: String,
    val pickup: String,
    val drop: String,
    val priceText: String,
    val status: OrderStatus
)

enum class OrderStatus(val label: String) {
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    BOOKED_AGAIN("BOOKED AGAIN")
}

enum class OrderFilter {
    ALL, COMPLETED, CANCELLED, BOOKED_AGAIN
}

object OrderRepository {
    fun sampleOrders(): List<OrderItem> = listOf(
        OrderItem(
            orderId = "#ORD12345",
            title = "Four Wheeler",
            meta = "05 Feb, 4:46 PM",
            pickup = "741, Gumanwara",
            drop = "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India",
            priceText = "\u20B9 229.0",
            status = OrderStatus.CANCELLED
        ),
        OrderItem(
            orderId = "#ORD12346",
            title = "Four Wheeler",
            meta = "05 Feb, 4:46 PM",
            pickup = "741, Gumanwara",
            drop = "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India",
            priceText = "\u20B9 229.0",
            status = OrderStatus.CANCELLED
        ),
        OrderItem(
            orderId = "#ORD12347",
            title = "Four Wheeler",
            meta = "05 Feb, 4:46 PM",
            pickup = "332, Gumanwara",
            drop = "GC72+GGV, Kamrari, Madhya Pradesh 475661, India",
            priceText = "\u20B9 1515.0",
            status = OrderStatus.CANCELLED
        ),
        OrderItem(
            orderId = "#ORD12348",
            title = "Four Wheeler",
            meta = "05 Feb, 4:46 PM",
            pickup = "332, Gumanwara",
            drop = "GC72+GGV, Kamrari, Madhya Pradesh 475661, India",
            priceText = "\u20B9 1634.0",
            status = OrderStatus.COMPLETED
        ),
        OrderItem(
            orderId = "#ORD12349",
            title = "Four Wheeler",
            meta = "05 Feb, 4:46 PM",
            pickup = "332, Gumanwara",
            drop = "GC72+GGV, Kamrari, Madhya Pradesh 475661, India",
            priceText = "\u20B9 1634.0",
            status = OrderStatus.BOOKED_AGAIN
        )
    )
}
