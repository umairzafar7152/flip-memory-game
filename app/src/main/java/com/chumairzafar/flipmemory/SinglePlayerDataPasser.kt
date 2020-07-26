package com.chumairzafar.flipmemory

interface SinglePlayerDataPasser {
    fun onDataPass(pFragment: String, pAttempts: Int, pLevelCompleted: Boolean)
    fun isGameOver(flag: Boolean)
}