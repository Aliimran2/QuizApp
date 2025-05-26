package com.miassolutions.quiztime.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.miassolutions.quiztime.R
import com.miassolutions.quiztime.databinding.DialogScoreBinding

class ScoreDialogFragment : DialogFragment() {


    private var score: Int = 0
    private var totalQuestions: Int = 0

    companion object {
        fun newInstance(score: Int, totalQuestions: Int): ScoreDialogFragment {
            val fragment = ScoreDialogFragment()
            val args = Bundle().apply {
                putInt("score", score)
                putInt("totalQuestions", totalQuestions)
            }
            fragment.arguments = args
            return fragment

        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        isCancelable = false

        val binding = DialogScoreBinding.inflate(layoutInflater)

        score = arguments?.getInt("score") ?: 0
        totalQuestions = arguments?.getInt("totalQuestions") ?: 0

        val percentage = (score * 100) / totalQuestions

        binding.apply {
            scoreProgress.progress = percentage
            tvScore.text = "$percentage%"

            if (percentage > 40) {
                tvScoreMsg.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                tvScoreMsg.text = "Congrats! You have won"

            } else {
                tvScoreMsg.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                tvScoreMsg.text = "Oops! You have failed"
            }

            tvScoreResult.text = "$score out of $totalQuestions"

            btnFinish.setOnClickListener {
                dismiss()
                activity?.finish()
            }
        }


        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        //back key press handle
        dialog.setOnKeyListener { _, keyCode, _ ->
            keyCode == KeyEvent.KEYCODE_BACK
        }

        return dialog
    }
}