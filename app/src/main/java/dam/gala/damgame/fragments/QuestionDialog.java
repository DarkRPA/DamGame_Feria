package dam.gala.damgame.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.res.ResourcesCompat;

import com.example.damgame.R;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import dam.gala.damgame.activities.GameActivity;
import dam.gala.damgame.model.Question;
import dam.gala.damgame.utils.GameUtil;

/**
 * Clase encargada de mostrar el dialogo con la pregunta
 * @author Iordache Mihai Laurentiu
 * @version 1.0
 */
public class QuestionDialog extends AppCompatDialogFragment {
    private List<Integer> indiceCorrectas;
    private int questionType;
    private GameActivity gameActivity;

    // CHECKBOX
    private Map<CheckBox, Integer> checkboxMap = new HashMap<>();
    private CheckBoxGroup checkBoxGroup;

    // SPINNER
    private Spinner spinner;

    private Question question;


    public QuestionDialog(Question question, int questionType, GameActivity interfaceDialog) {
        this.questionType = questionType;
        this.question = question;
        this.indiceCorrectas = this.question.getIndiceRespuestasCorrectas();
        this.gameActivity = interfaceDialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext(), AlertDialog.THEME_HOLO_LIGHT);

        final AtomicBoolean btnPositiveRadioEnable = new AtomicBoolean(false);
        final AtomicBoolean btnPositiveVidaPuntosEnable = new AtomicBoolean(false);
        final AtomicBoolean response = new AtomicBoolean(false);
        final String[] vidaPuntos = {""};

        View mView = requireActivity().getLayoutInflater().inflate(R.layout.activity_question, null);

        TextView tvPregunta = mView.findViewById(R.id.tvEnunciado);
        tvPregunta.setText(question.getQuestion());


        mBuilder.setView(mView);
        setTema(mView);

        if (questionType == Question.SIMPLE) {
            question.setType(Question.SIMPLE);

            setRadioButtonsAnswers(question, mView);
        } else if (questionType == Question.MULTIPLE) {
            question.setType(Question.MULTIPLE);

            setCheckboxAnswers(question, mView);
        } else if (questionType == Question.LIST) {
            question.setType(Question.LIST);
            setSpinnerAnswer(question, mView);
        }


        setPositiveButtonRadioButton(mBuilder, response, vidaPuntos);

        AlertDialog dialog = mBuilder.create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.show();

        final Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btnPositive.setEnabled(false);

        setRadioButtonsListener(mView, btnPositiveRadioEnable, btnPositiveVidaPuntosEnable, dialog, response);

        setVidaPuntosDificultadAlta(mView, vidaPuntos, btnPositiveVidaPuntosEnable, btnPositiveRadioEnable, dialog);

        return dialog;
    }


    /**
     * Establece las respuestas de tipo Radio Button
     *
     * @param question La pregunta de la que coger las respuestas
     * @param mView    La vista donde se encuentra el Radio Group
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setRadioButtonsAnswers(Question question, View mView) {
        final RadioGroup radioGroup = (RadioGroup) mView.findViewById(R.id.rgroup);

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[] {
                        Color.BLACK, //disabled
                        Color.WHITE //enabled
                }
        );
        for (int i = 0; i < question.getAnswers().size(); i++) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setButtonTintList(colorStateList);
            radioButton.setTextColor(Color.BLACK);
            radioButton.setText(question.getAnswers().get(i));
            radioButton.setId(i);
            radioGroup.addView(radioButton);
        }
    }

    /**
     * Establece las respuestas de tipo Radio Button
     *
     * @param pregunta La pregunta de la que coger las respuestas
     * @param mView    La vista donde se encuentra el Radio Group
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setCheckboxAnswers(Question pregunta, View mView) {
        final RadioGroup radioGroup = (RadioGroup) mView.findViewById(R.id.rgroup);

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[] {
                        Color.BLACK, //disabled
                        Color.WHITE //enabled
                }
        );

        int option = 0;
        for (int i = 0; i < pregunta.getAnswers().size(); i++) {
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setButtonTintList(colorStateList);
            checkBox.setTextColor(Color.WHITE);
            checkBox.setText(pregunta.getAnswers().get(i));
            checkBox.setId(i);

            radioGroup.addView(checkBox);

            checkboxMap.put(checkBox, option++);
        }
    }


    /**
     * Establece un listener al radio group
     *
     * @param mView                       La vista del radio group
     * @param btnPositiveRadioEnable      variable atomica que define si se puede habilitar el boton positivo
     * @param btnPositiveVidaPuntosEnable variable atomica que define si se puede habilitar el boton positivo
     * @param dialog                      El dialogo
     * @param response                    variable atomica que define la respuesta del usuario, el radio button elegido
     */
    private void setRadioButtonsListener(View mView,
                                         final AtomicBoolean btnPositiveRadioEnable,
                                         final AtomicBoolean btnPositiveVidaPuntosEnable,
                                         AlertDialog dialog,
                                         final AtomicBoolean response) {

        final RadioGroup radioGroup = (RadioGroup) mView.findViewById(R.id.rgroup);
        final RadioGroup radioGroupVidaPuntos = (RadioGroup) mView.findViewById(R.id.rgPuntosVida);
        final Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        if (question.getType() == Question.SIMPLE) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == indiceCorrectas.get(0)) response.set(true);

                    if (group.getCheckedRadioButtonId() != -1) {
                        btnPositiveRadioEnable.set(true);
                        btnPositive.setBackgroundColor(Color.BLACK);
                        btnPositive.setTextColor(Color.WHITE);

                    }

                    if (btnPositiveRadioEnable.get() && btnPositiveVidaPuntosEnable.get() || radioGroupVidaPuntos.getVisibility() != View.VISIBLE) {
                        btnPositive.setEnabled(true);
                        btnPositive.setBackgroundColor(Color.BLACK);
                        btnPositive.setTextColor(Color.WHITE);

                    }
                }
            });
        } else if (question.getType() == Question.MULTIPLE) {
            this.checkBoxGroup = new CheckBoxGroup<>(checkboxMap,
                new CheckBoxGroup.CheckedChangeListener<Integer>() {
                    @Override
                    public void onCheckedChange(ArrayList<Integer> values) {
                        if (question.getDifficulty() == GameUtil.PREGUNTA_COMPLEJIDAD_ALTA){
                            if (values.size() > 0 && radioGroupVidaPuntos.getCheckedRadioButtonId() != -1) {
                                btnPositive.setEnabled(true);
                                btnPositive.setBackgroundColor(Color.BLACK);
                                btnPositive.setTextColor(Color.WHITE);
                            } else {
                                btnPositive.setEnabled(false);
                                btnPositive.setBackgroundColor(Color.BLACK);
                                btnPositive.setTextColor(Color.WHITE);

                            }
                        }else{
                            if (values.size() > 0) {
                                btnPositive.setEnabled(true);
                                btnPositive.setBackgroundColor(Color.BLACK);
                                btnPositive.setTextColor(Color.WHITE);

                            } else {
                                btnPositive.setEnabled(false);
                                btnPositive.setBackgroundColor(Color.BLACK);

                                btnPositive.setTextColor(Color.WHITE);

                            }
                        }
                    }
                });
        } else if (question.getType() == Question.LIST){

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                    if (radioGroupVidaPuntos.getCheckedRadioButtonId() != -1)
                        btnPositive.setEnabled(true);
                    if (position == indiceCorrectas.get(0)) response.set(true);


                    if (btnPositiveRadioEnable.get() && btnPositiveVidaPuntosEnable.get() || radioGroupVidaPuntos.getVisibility() != View.VISIBLE) {
                        btnPositive.setEnabled(true);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


    }

    /**
     * Establece las respuestas de tipo Radio Button
     *
     * @param pregunta La pregunta de la que coger las respuestas
     * @param mView    La vista donde se encuentra el Radio Group
     */
    private void setSpinnerAnswer(Question pregunta, View mView) {
        LinearLayout lyPreguntas = mView.findViewById(R.id.lyPreguntas);
        final Spinner spinner = new Spinner(getContext());

        spinner.setMinimumWidth(1500);
        spinner.setMinimumHeight(100);
        spinner.setDropDownWidth(1000);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, pregunta.getAnswers());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        lyPreguntas.addView(spinner);

        lyPreguntas.findViewById(R.id.rgroup).setVisibility(View.GONE);
        this.spinner = spinner;
    }

    /**
     * Establece el boton positivo
     *
     * @param mBuilder   El builder del dialogo
     * @param response   variable atomica que define la respuesta del usuario, el radio button elegido
     * @param vidaPuntos Define la respuesta del usuario, si ha elegido vida o puntos
     */
    private void setPositiveButtonRadioButton(AlertDialog.Builder mBuilder, final AtomicBoolean response, final String[] vidaPuntos) {
        if (question.getType() == Question.SIMPLE || question.getType() == Question.LIST) {
            mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (response.get()) {
                        boolean vida = false;
                        if (vidaPuntos[0].equals("Vida")) vida = true;

                        Toast.makeText(requireContext(), "Has acertado! Has ganado " + (vida ? " 1 vida! " : question.getPOINTS() + " puntos"), Toast.LENGTH_SHORT).show();

                        gameActivity.getPlay().setScoreAnswers(gameActivity.getPlay().getScoreAnswers() + 1);

                        if (!vida) {
                            gameActivity.getPlay().setPoints(gameActivity.getPlay().getPoints() + question.getPOINTS());
                        }else{
                            gameActivity.getPlay().setHasWonLife(true);
                            gameActivity.getPlay().setLifes(gameActivity.getPlay().getLifes() + 1);
                        }
                    }else{
                        Toast.makeText(requireContext(), "No has acertado, lástima!", Toast.LENGTH_SHORT).show();
                    }

                    gameActivity.getGameView().setStopGame(false);
                    gameActivity.getGameView().restart();
                }
            });
        } else if (question.getType() == Question.MULTIPLE) {
            mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (question.getDifficulty() == GameUtil.PREGUNTA_COMPLEJIDAD_ALTA) {
                        if (checkBoxGroup.getValues().size() > 0) {
                            boolean vida = false;
                            if (vidaPuntos[0].equals("Vida")) vida = true;

                            int correctas = 0;
                            int cantidadCorrectas = indiceCorrectas.size();

                            for (Integer pos : (ArrayList<Integer>) checkBoxGroup.getValues()) {
                                if (indiceCorrectas.contains(pos)) {
                                    correctas++;
                                } else if (!indiceCorrectas.contains(pos)) {
                                    correctas--;
                                }
                            }

                            if (correctas == cantidadCorrectas) {
                                Toast.makeText(requireContext(), "Has acertado! Has ganado " + (vida ? "1 vida! " : question.getPOINTS() + "puntos"), Toast.LENGTH_SHORT).show();

                                gameActivity.getPlay().setScoreAnswers(gameActivity.getPlay().getScoreAnswers() + 1);

                                if (!vida){
                                    gameActivity.getPlay().setPoints(gameActivity.getPlay().getPoints() + question.getPOINTS());
                                }else{
                                    gameActivity.getPlay().setHasWonLife(true);
                                    gameActivity.getPlay().setLifes(gameActivity.getPlay().getLifes() + 1);
                                }
                            } else {
                                Toast.makeText(requireContext(), "No has acertado! Lástima", Toast.LENGTH_SHORT).show();
                            }

                            checkBoxGroup.setValues(new ArrayList<Integer>());

                            gameActivity.getGameView().setStopGame(false);
                            gameActivity.getGameView().restart();
                        }
                    } else {
                        if (checkBoxGroup.getValues().size() > 0) {
                            vidaPuntos[0] = "Puntos";

                            int correctas = 0;
                            int cantidadCorrectas = indiceCorrectas.size();

                            for (Integer pos : (ArrayList<Integer>) checkBoxGroup.getValues()) {
                                if (indiceCorrectas.contains(pos)) {
                                    correctas++;
                                } else if (!indiceCorrectas.contains(pos)) {
                                    correctas--;
                                }
                            }

                            if (correctas == cantidadCorrectas) {
                                Toast.makeText(requireContext(), "Has acertado! Has ganado " + question.getPOINTS() + "puntos", Toast.LENGTH_SHORT).show();

                                gameActivity.getPlay().setScoreAnswers(gameActivity.getPlay().getScoreAnswers() + 1);

                                gameActivity.getPlay().setPoints(gameActivity.getPlay().getPoints() + question.getPOINTS());
                            } else {
                                Toast.makeText(requireContext(), "No has acertado! Lástima", Toast.LENGTH_SHORT).show();

                            }

                            checkBoxGroup.setValues(new ArrayList<Integer>());

                            gameActivity.getGameView().setStopGame(false);
                            gameActivity.getGameView().restart();
                        }
                    }
                }
            });
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getDialog().getWindow().getDecorView().setSystemUiVisibility(getActivity().getWindow().getDecorView().getSystemUiVisibility());

        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //Clear the not focusable flag from the window
                getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

                //Update the WindowManager with the new attributes (no nicer way I know of to do this)..
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                wm.updateViewLayout(getDialog().getWindow().getDecorView(), getDialog().getWindow().getAttributes());
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Muestra la eleccion, vida o puntos en caso de que la dificultad sea alta
     *
     * @param mView                       La vista del dialogo
     * @param vidaPuntos                  El array donde indica que desea
     * @param btnPositiveVidaPuntosEnable variable atomica que define si se puede habilitar el boton positivo
     * @param btnPositiveRadioEnable      variable atomica que define si se puede habilitar el boton positivo
     * @param dialog                      El dialogo
     */
    private void setVidaPuntosDificultadAlta(View mView, final String[] vidaPuntos,
                                             final AtomicBoolean btnPositiveVidaPuntosEnable,
                                             final AtomicBoolean btnPositiveRadioEnable,
                                             AlertDialog dialog) {

        final RadioGroup radioGroupVidaPuntos = (RadioGroup) mView.findViewById(R.id.rgPuntosVida);
        final Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        radioGroupVidaPuntos.setVisibility(View.INVISIBLE);

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[] {
                        Color.BLACK, //disabled
                        Color.WHITE //enabled
                }
        );

        if (question.getDifficulty() == GameUtil.PREGUNTA_COMPLEJIDAD_ALTA) {
            radioGroupVidaPuntos.setVisibility(View.VISIBLE);

            if (question.getType() == Question.SIMPLE) {
                radioGroupVidaPuntos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = group.findViewById(checkedId);

                            radioButton.setButtonTintList(colorStateList);
                            btnPositive.setBackgroundColor(Color.BLACK);

                        vidaPuntos[0] = radioButton.getText().toString();

                        if (group.getCheckedRadioButtonId() != -1) {
                            btnPositiveVidaPuntosEnable.set(true);
                        }

                        if (btnPositiveRadioEnable.get() && btnPositiveVidaPuntosEnable.get()) {
                            btnPositive.setEnabled(true);
                        }
                    }
                });
            } else if (question.getType() == Question.MULTIPLE) {
                radioGroupVidaPuntos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = group.findViewById(checkedId);
                        radioButton.setButtonTintList(colorStateList);

                        vidaPuntos[0] = radioButton.getText().toString();

                        if (group.getCheckedRadioButtonId() != -1 && checkBoxGroup.getValues().size() > 0) {
                            btnPositive.setEnabled(true);
                            btnPositiveVidaPuntosEnable.set(true);
                        }

                        if (btnPositiveRadioEnable.get() && btnPositiveVidaPuntosEnable.get() && checkBoxGroup.getValues().size() > 0) {
                            btnPositive.setEnabled(true);
                        }
                    }
                });
            } else if (question.getType() == Question.LIST){
                radioGroupVidaPuntos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = group.findViewById(checkedId);
                        radioButton.setButtonTintList(colorStateList);

                        vidaPuntos[0] = radioButton.getText().toString();

                        if (group.getCheckedRadioButtonId() != -1){
                            btnPositive.setEnabled(true);
                            btnPositiveVidaPuntosEnable.set(true);
                        }

                        if (btnPositiveRadioEnable.get() && btnPositiveVidaPuntosEnable.get()){
                            btnPositive.setEnabled(true);
                        }
                    }
                });
            }

        } else {
            vidaPuntos[0] = "Puntos";
            LinearLayout linearLayout = mView.findViewById(R.id.lyVidaPuntos);
            linearLayout.setVisibility(View.GONE);
        }
    }

    public void setTema(View mView){
        LinearLayout lyQuestionDialog = mView.findViewById(R.id.lyQuestionMain);
        LinearLayout lyQuestion = mView.findViewById(R.id.lyQuestionEnunciado);
        LinearLayout lyPuntos = mView.findViewById(R.id.lyVidaPuntos);
        LinearLayout rgPuntos = mView.findViewById(R.id.rgPuntosVida);

        String tema = getActivity().getSharedPreferences(getActivity().getTitle().toString(),
                Context.MODE_PRIVATE).getString("theme_setting","100");

        switch(tema){
            case "@string/TEMA_SELVA":
            default:
                lyQuestionDialog.setBackground(ResourcesCompat.getDrawable(
                        this.getResources(),R.drawable.jungle_dialog_border_out,
                        this.getActivity().getTheme()));
                lyQuestion.setBackground(ResourcesCompat.getDrawable(
                        this.getResources(),R.drawable.jungle_dialog_border_out,
                        this.getActivity().getTheme()));
                lyPuntos.setBackground(ResourcesCompat.getDrawable(
                        this.getResources(),R.drawable.jungle_dialog_border_out,
                        this.getActivity().getTheme()));
                rgPuntos.setBackground(ResourcesCompat.getDrawable(
                        this.getResources(),R.drawable.jungle_dialog_border_in,
                        this.getActivity().getTheme()));
                break;
        }
    }
}