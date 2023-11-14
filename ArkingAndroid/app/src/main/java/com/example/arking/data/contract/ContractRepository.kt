package com.example.arking.data.contract

import com.example.arking.model.Contract
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContractRepository @Inject constructor(
    private val contractDao: ContractDao
) {
    suspend fun createContract(contract: Contract) {
        var registered = contractDao.loadAllById(contract.id)
        if(registered == null){
            contractDao.insert(contract)
        }
        else{
            registered.name = contract.name
            registered.description = contract.description
            registered.status = contract.status
            contractDao.upadate(registered)
        }
    }
    fun getAll() = contractDao.getAll()
    fun getByIdFlow(id:Int) = contractDao.loadAllByIdFlow(id)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ContractRepository? = null

        fun getInstance(contractDao: ContractDao) =
            instance ?: synchronized(this) {
                instance ?: ContractRepository(contractDao).also { instance = it }
            }
    }
}