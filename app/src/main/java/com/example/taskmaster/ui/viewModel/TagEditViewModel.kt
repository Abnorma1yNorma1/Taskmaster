package com.example.taskmaster.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmaster.model.Tag
import com.example.taskmaster.model.ValidateState
import com.example.taskmaster.repository.TagRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TagEditViewModel (private val tagRepository: TagRepository):ViewModel(){

    private val job = SupervisorJob()
    private val tagScope = CoroutineScope(job + Dispatchers.IO)

    private val _tagState: MutableLiveData<ValidateState> = MutableLiveData(ValidateState.DEFAULT)
    val tagState: LiveData<ValidateState> = _tagState

    private var tagId:Int? = null
    private var tagName:String? = null


    fun editTag(name: String){
        val tag = isDataValid(name)?:return
        tagScope.launch {
            if(tagId!=null && tagRepository.isExists(tagId!!)){
                tagRepository.updateTagName(tagId!!,name)
            }else tagRepository.insertTag(name)
            tagId = null
            tagName = null
        }
    }

    private fun isDataValid(tagName: String?): Tag? {
        return if (tagName.isNullOrBlank()){
            _tagState.value = ValidateState.FALSE
            null
        }else{
            _tagState.value = ValidateState.TRUE
            Tag(tagName)
        }
    }

    fun setId(id: Int){
        tagId = id
    }

}

class TagEditViewModelFactory(
    private val repository: TagRepository): ViewModelProvider.Factory{
        override fun <T:ViewModel> create(modelClass: Class<T>):T{
            if (modelClass.isAssignableFrom(TagEditViewModel::class.java)){
                return TagEditViewModel(repository) as T
            }
            throw java.lang.IllegalArgumentException("Unknown viewModel!")
        }
    }
