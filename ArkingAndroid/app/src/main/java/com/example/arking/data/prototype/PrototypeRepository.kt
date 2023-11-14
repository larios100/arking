package com.example.arking.data.prototype

import com.example.arking.model.Prototype
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrototypeRepository @Inject constructor(
    private val prototypeDao: PrototypeDao
) {
    suspend fun createContract(prototype: Prototype) {
        var registered = prototypeDao.loadAllById(prototype.id)
        if(registered == null){
            prototypeDao.insert(prototype)
        }
        else{
            registered.name = prototype.name
            prototypeDao.upadate(registered)
        }
    }
    fun getAll() = prototypeDao.getAll()

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PrototypeRepository? = null

        fun getInstance(prototypeDao: PrototypeDao) =
            instance ?: synchronized(this) {
                instance ?: PrototypeRepository(prototypeDao).also { instance = it }
            }
    }
}