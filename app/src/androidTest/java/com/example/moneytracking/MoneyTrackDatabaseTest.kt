package com.example.moneytracking

//import androidx.room.Room
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.platform.app.InstrumentationRegistry
//import com.example.moneytracking.database.MoneyTrack
//import com.example.moneytracking.database.MoneyTrackDatabase
//import com.example.moneytracking.database.MoneyTrackDatabaseDao
//import org.junit.Assert.assertEquals
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.io.IOException
//
//@RunWith(AndroidJUnit4::class)
//class MoneyTrackDatabaseTest {
//        private lateinit var moneyDao: MoneyTrackDatabaseDao
//    private lateinit var db: MoneyTrackDatabase
//
//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
////         Using an in-memory database because the information stored here disappears when the
////         process is killed.
//        db = Room.inMemoryDatabaseBuilder(context, MoneyTrackDatabase::class.java)
//                 //Allowing main thread queries, just for testing.
//                .allowMainThreadQueries()
//                .build()
//        moneyDao = db.moneyTrackDatabaseDao
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetNight() {
//        val night = MoneyTrack()
//        moneyDao.insert(night)
//        val tonight = moneyDao.getAllSumExpensive()
//        assertEquals(tonight?.sumExpense, 0L)
//    }
//}