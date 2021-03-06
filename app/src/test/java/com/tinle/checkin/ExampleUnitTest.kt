package com.tinle.checkin

import android.arch.lifecycle.MutableLiveData
import com.tinle.checkin.api.GposService
import com.tinle.checkin.api.GPOSApi
import com.tinle.checkin.data.Post
import com.tinle.checkin.vm.MainViewModel
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.`when`

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Mock
    lateinit var mainViewModel:MainViewModel

    @Mock
    lateinit var gposService:GposService

    @Mock
    lateinit var api: GPOSApi




    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testDataPull() {
        val sourceData:MutableLiveData<List<Post>> = MutableLiveData()
        sourceData.value = getSampleData()
        //`when`(mainViewModel.setResult(emptyList())).then {  }
        `when`(mainViewModel.getData())
                .thenReturn(sourceData)

        val returnData = mainViewModel.getData()
        Assert.assertEquals(sourceData, returnData)
    }

    private fun getSampleData():List<Post> {
        val data:MutableList<Post> = mutableListOf()
        for(i in 0..5) {
            data.add(Post("user" + i, "id " + i, " title " + i, "body " +i))
        }
        return data
    }

}
