package com.example

import com.example.data.model.ActivityLevel
import com.example.data.model.CalorieProfile
import com.example.data.model.UserGender
import com.example.data.model.WeightGoal
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testCalorieProfileCalculations() {
    val femaleProfile = CalorieProfile(
      age = 24,
      weightKg = 58f,
      heightCm = 165f,
      gender = UserGender.FEMALE,
      activityLevel = ActivityLevel.LIGHT,
      goal = WeightGoal.MAINTAIN
    )

    val bmr = femaleProfile.calculateBmr()
    assertTrue(bmr in 1200..1500)

    val tdee = femaleProfile.calculateTdee()
    assertTrue(tdee > bmr)

    val targetCalories = femaleProfile.calculateTargetCalories()
    assertEquals(tdee, targetCalories)

    val macros = femaleProfile.calculateMacros()
    assertTrue(macros.proteinGrams > 0)
    assertTrue(macros.carbsGrams > 0)
    assertTrue(macros.fatGrams > 0)
    assertTrue(macros.waterLiters >= 1.8f)
  }

  @Test
  fun testCalorieWeightLossGoal() {
    val profile = CalorieProfile(
      age = 24,
      weightKg = 60f,
      heightCm = 165f,
      gender = UserGender.FEMALE,
      activityLevel = ActivityLevel.MODERATE,
      goal = WeightGoal.LOSE_WEIGHT
    )

    val tdee = profile.calculateTdee()
    val target = profile.calculateTargetCalories()
    assertEquals(tdee - 400, target)
  }

  @Test
  fun testRomanticQuotesContainSweetPhrases() {
    val quotes = com.example.data.quotes.QuoteProvider.initialQuotes
    assertTrue(quotes.isNotEmpty())
    val hasLoveQuote = quotes.any { it.text.contains("دوستت دارم") }
    val hasBeautyQuote = quotes.any { it.text.contains("خوشگل") }
    assertTrue(hasLoveQuote)
    assertTrue(hasBeautyQuote)
  }

  @Test
  fun testOnlineMusicLegendsAvailable() {
    val tracks = com.example.data.music.OnlineMusicProvider.curatedTracks
    assertTrue(tracks.isNotEmpty())
    val artists = tracks.map { it.artist }
    assertTrue(artists.contains("هایده"))
    assertTrue(artists.contains("سیاوش قمیشی"))
    assertTrue(artists.contains("مهستی"))
    assertTrue(artists.contains("گوگوش"))
  }
}
