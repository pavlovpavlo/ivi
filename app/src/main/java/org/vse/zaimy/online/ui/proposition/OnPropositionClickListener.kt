package org.vse.zaimy.online.ui.proposition

import org.vse.zaimy.online.pojo.db.Proposition

interface OnPropositionClickListener {
    fun onDetailClick(proposition: Proposition)
    fun onOrderClick(proposition: Proposition)
}