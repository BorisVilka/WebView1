package org.webview1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import org.webview1.databinding.ActivityGameBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityGameBinding binding;
    private int ind;
    private boolean enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ind = 0;
        enabled = true;
        bind();
        binding.button1.setOnClickListener(this::onClick);
        binding.button2.setOnClickListener(this::onClick);
        binding.button3.setOnClickListener(this::onClick);
        binding.button4.setOnClickListener(this::onClick);
    }


    Question[] questions = {
            new Question(R.mipmap.ic1_foreground,0,"What is the name of this player",
                    new String[]{
                            "1","2","3","4"
                    }),
            new Question(R.mipmap.ic_launcher,0,"SEcond",
                    new String[]{
                            "1","2","3","4"
                    }),
    };
    private void bind() {
        if(ind>=questions.length) {
            finish();
            return;
        }
        Question question = questions[ind];
        binding.setQuestion(question);
        binding.imageView.setImageDrawable(getDrawable(question.id));
    }

    @Override
    public void onClick(View view) {
        if(!enabled) return;
        enabled = false;
        int ans = 0;
        switch (view.getId()) {
            case R.id.button1:
                ans = 0;
                break;
            case R.id.exit:
                ans = 1;
                break;
            case R.id.button3:
                ans = 2;
                break;
            case R.id.button4:
                ans = 3;
                break;
        }
        if(ans==questions[ind].right) {
            view.setBackgroundColor(getColor(R.color.right));
        } else {
            view.setBackgroundColor(getColor(R.color.wrong));
            findViewById(R.id.button3).setBackgroundColor(getColor(R.color.right));
        }
        Completable.create(emitter -> {
            ind++;
            emitter.onComplete();
        }).delaySubscription(2000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onComplete() {
                enabled = true;
                refresh();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }
    void refresh() {
        binding.button1.setBackgroundColor(getColor(R.color.back_button));
        binding.button2.setBackgroundColor(getColor(R.color.back_button));
        binding.button3.setBackgroundColor(getColor(R.color.back_button));
        binding.button4.setBackgroundColor(getColor(R.color.back_button));
        bind();
    }
}