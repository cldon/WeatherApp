package hu.ait.weatherinfo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import hu.ait.weatherinfo.data.City
import kotlinx.android.synthetic.main.new_city_dialog.view.*
//import hu.ait.weatherinfo.data.Weather
//import kotlinx.android.synthetic.main.new_weather_dialog.view.*
import java.lang.RuntimeException

class AddCityDialog : DialogFragment() {
    interface CityHandler {
        fun newCityCreated(item: City)
//        fun toBuyUpdated(item: ToBuy)
    }

    private lateinit var cityHandler: CityHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is CityHandler) {
            cityHandler = context
        } else {
            throw RuntimeException("The activity does not implement the CityHandler interface")
        }
    }

    //private lateinit var spinCategory: Spinner
    //private lateinit var etToBuyText: EditText
    //private lateinit var etDescription: EditText
    //private lateinit var etPrice: EditText
    //private lateinit var cbBought2: CheckBox
    private lateinit var etCity: EditText


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Add New City")

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.new_city_dialog, null
        )
        //etTodoDate = rootView.findViewById(R.id.etTodoText)
//        spinCategory = rootView.spinCategory
//        etToBuyText = rootView.etToBuy
//        etDescription = rootView.etDescription
//        etPrice = rootView.etEstPrice
//        cbBought2 = rootView.cbBought2
        etCity = rootView.et_city

        builder.setView(rootView)

        val arguments = this.arguments

        // IF I AM IN EDIT MODE
//        if (arguments != null && arguments.containsKey(
//                ScrollingActivity.KEY_ITEM_TO_EDIT)) {
//
//            val toBuyItem = arguments.getSerializable(
//                ScrollingActivity.KEY_ITEM_TO_EDIT
//            ) as ToBuy
//
//            spinCategory.setSelection(toBuyItem.category)
//    //            spinCategory.setText(toBuyItem.category)
//            etToBuyText.setText(toBuyItem.name)
//            etDescription.setText(toBuyItem.description)
//            etPrice.setText(toBuyItem.price.toString())
//            cbBought2.isChecked = toBuyItem.bought
//
//            builder.setTitle(getString(R.string.editshoppingitem))
//        }

        builder.setPositiveButton("OK") {
                dialog, witch -> // empty
        }
        builder.setNegativeButton("Cancel") {
            dialog, witch ->
        }

        return builder.create()
    }


    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            //         etCategory = rootView.etCategory
            //        etToBuyText = rootView.etToBuy
            //        etDescription = rootView.etDescription
            //        etPrice = rootView.etEstPrice
//            var err = false
    //            if (spinCategory) {
    //                err = true
    //                spinCategory.error = "This field cannot be empty"
    //            }
//            if (etToBuyText.text.isEmpty()) {
//                err = true
//                etToBuyText.error = getString(R.string.err)
//            }
//            if (etDescription.text.isEmpty()) {
//                err = true
//                etDescription.error = getString(R.string.err)
//            }
//            if (etPrice.text.isEmpty()) {
//                err = true
//                etPrice.error = getString(R.string.err)
//            }

            if (etCity.text.isEmpty()) {
                etCity.error = "Must enter city"
            } else {
                handleCityCreate()
                dialog.dismiss()
            }

//            if (!err) {
//                val arguments = this.arguments
//                if (arguments != null && arguments.containsKey(ScrollingActivity.KEY_ITEM_TO_EDIT)) {
//                    handleToBuyEdit()
//                } else {
//                    handleToBuyCreate()
//                }
//
//                dialog.dismiss()
//            }
        }
    }

    private fun handleCityCreate() {
        cityHandler.newCityCreated(
            City(null, etCity.text.toString())
        )
    }


//    private fun handleToBuyCreate() {
//
//        toBuyHandler.toBuyCreated(
//            ToBuy(
//                null,
//                spinCategory.selectedItemPosition,
//                etToBuyText.text.toString(),
//                etDescription.text.toString(),
//                etPrice.text.toString().toFloat(),
//                cbBought2.isChecked
//            )
//        )
//    }

//    private fun handleToBuyEdit() {
//        val toBuyToEdit = arguments?.getSerializable(
//            ScrollingActivity.KEY_ITEM_TO_EDIT
//        ) as ToBuy
//
//        toBuyToEdit.category = spinCategory.selectedItemPosition
//
//        toBuyToEdit.name = etToBuyText.text.toString()
//        toBuyToEdit.description = etDescription.text.toString()
//        toBuyToEdit.price = etPrice.text.toString().toFloat()
//        toBuyToEdit.bought = cbBought2.isChecked
//
//        toBuyHandler.toBuyUpdated(toBuyToEdit)
//    }

}