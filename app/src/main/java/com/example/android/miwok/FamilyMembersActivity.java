package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyMembersActivity extends AppCompatActivity {

    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override

        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                mMediaPlayer.release();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Mother", "माता", "Mātā", R.drawable.family_mother, R.raw.number_one));
        words.add(new Word("Father", "पिता", "Pitā", R.drawable.family_father, R.raw.number_two));
        words.add(new Word("Maternal Grandfather", "मातामहः", "Mātāmahaḥ", R.drawable.family_grandfather, R.raw.number_two));
        words.add(new Word("Paternal Grandfather", "पितामहः", "Pitāmahaḥ", R.drawable.family_grandfather, R.raw.number_one));
        words.add(new Word("Maternal Grandmother", "मातामही", "Mātāmahī", R.drawable.family_grandmother, R.raw.number_one));
        words.add(new Word("Paternal Grandmother", "पितामही", "Pitāmahī", R.drawable.family_grandmother, R.raw.number_one));
        words.add(new Word("Husband", "पतिः", "Patiḥ", R.drawable.family_father, R.raw.number_one));
        words.add(new Word("Wife", "पत्नी", "Patnī", R.drawable.family_mother, R.raw.number_one));
        words.add(new Word("Daughter", "पुत्री", "Putrī", R.drawable.family_daughter, R.raw.number_one));
        words.add(new Word("Son", "पुत्रः", "Putraḥ", R.drawable.family_son, R.raw.number_one));
        words.add(new Word("Elder Brother", "ज्येष्ठभ्राता", "Jyeṣṭhabhrātā", R.drawable.family_older_brother, R.raw.number_one));
        words.add(new Word("Younger Brother", "कनिष्ठभ्राता", "Kaniṣṭhabhrātā", R.drawable.family_younger_brother, R.raw.number_one));
        words.add(new Word("Elder Sister", "ज्येष्ठभगिनी", "Jyeṣṭhabhaginī", R.drawable.family_older_sister, R.raw.number_one));
        words.add(new Word("Younger Sister", "कनिष्ठभगिनी", "Kaniṣṭhabhaginī", R.drawable.family_younger_sister, R.raw.number_one));
        words.add(new Word("Maternal Uncle", "मातुलः", "Mātulaḥ", R.drawable.family_father, R.raw.number_one));
        words.add(new Word("Paternal Uncle", "पितृव्यः", "Pitṛvyaḥ", R.drawable.family_father, R.raw.number_one));
        words.add(new Word("Wife of Maternal Uncle", "मातुलानी", "Mātulanī", R.drawable.family_mother, R.raw.number_one));
        words.add(new Word("Wife of Paternal Uncle", "पितृव्या", "Pitṛvyā", R.drawable.family_mother, R.raw.number_one));
        words.add(new Word("Father-in-Law", "श्वशुरः", "Śvaśuraḥ", R.drawable.family_grandfather, R.raw.number_one));
        words.add(new Word("Mother-in-Law", "श्वश्रूः", "Śvaśrūḥ", R.drawable.family_grandmother, R.raw.number_one));
        words.add(new Word("Wife’s Brother", "श्यालः", "Śyālaḥ", R.drawable.family_father, R.raw.number_one));
        words.add(new Word("Wife’s Sister", "श्याली", "Śyālī", R.drawable.family_older_sister, R.raw.number_one));
        words.add(new Word("Husband’s Brother (Elder)", "ज्येष्ठभ्राता", "Jyeṣṭhabhrātā", R.drawable.family_father, R.raw.number_one));
        words.add(new Word("Husband’s Brother (Younger)", "देवरः", "Devaraḥ", R.drawable.family_older_brother, R.raw.number_one));
        words.add(new Word("Husband’s Sister", "ननान्दा", "Nanāndā", R.drawable.family_older_sister, R.raw.number_one));
        words.add(new Word("Grandson", "पौत्रः", "Pautraḥ", R.drawable.family_son, R.raw.number_one));
        words.add(new Word("Granddaughter", "पौत्री", "Pautrī", R.drawable.family_daughter, R.raw.number_one));
        words.add(new Word("Daughter’s Son", "दौहित्रः", "Dauhitraḥ", R.drawable.family_younger_brother, R.raw.number_one));
        words.add(new Word("Daughter’s Daughter", "दौहित्री", "Dauhitrī", R.drawable.family_younger_sister, R.raw.number_one));
        words.add(new Word("Friend", "सखा", "Sakhā", R.drawable.family_older_brother, R.raw.number_one));


        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);


                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.


                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(FamilyMembersActivity.this, word.getAudioResourceId());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }


    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}