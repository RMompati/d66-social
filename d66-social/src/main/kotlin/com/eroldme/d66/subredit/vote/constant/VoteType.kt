package com.eroldme.d66.subredit.vote.constant

/**
 * @author Mompati 'Patco' Keetile
 * @since 06-12-2022 @ 14:57
 *
 *
 */
enum class VoteType(private val direction: Int) {
    UP_VOTE(1),
    DOWN_VOTE(-1)
}