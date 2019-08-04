package ru.skillbranch.devintensive.viewmodels

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class ProfileViewModel : ViewModel() {

    private val repository: PreferencesRepository = PreferencesRepository
    private val profileData = MutableLiveData<Profile>()
    private val appTheme = MutableLiveData<Int>()
    private val repoIsValid = MutableLiveData<Boolean>()

    init {
        profileData.value = repository.getProfile()
    }

    fun getProfileData() : LiveData<Profile> = profileData

    fun saveProfileData(profile: Profile) {
        if (repoIsValid.value == false) {
            profile.repository = ""
        }
        repository.saveProfile(profile)
        profileData.value = profile
    }

    fun getTheme() : LiveData<Int> = appTheme

    fun switchTheme() {
        if (appTheme.value == AppCompatDelegate.MODE_NIGHT_YES) {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_NO
        } else {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_YES
        }
        repository.saveAppTheme(appTheme.value!!)
    }

    fun setRepoIsValid(isValid: Boolean) {
        repoIsValid.value = isValid
    }

    fun getRepoIsValid(): LiveData<Boolean> = repoIsValid
}