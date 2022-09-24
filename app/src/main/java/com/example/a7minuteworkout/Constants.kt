package com.example.a7minuteworkout;

class Constants {
    companion object {
        fun defaultExerciseList(): ArrayList<ExerciseModel> {
            val exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(1, "Jumping jacks", R.drawable.ic_jumping_jacks,
                    isCompleted = false, isSelected = false)
            exerciseList.add(jumpingJacks)
            val abdominalCrunch = ExerciseModel(2, "Abdominal crunch", R.drawable.ic_abdominal_crunch,
                    isCompleted = false, isSelected = false)
            exerciseList.add(abdominalCrunch)
            val highKneesRunningInPlace = ExerciseModel(3, "High knees running in place", R.drawable.ic_high_knees_running_in_place,
                    isCompleted = false, isSelected = false)
            exerciseList.add(highKneesRunningInPlace)
            val lunge = ExerciseModel(4, "Lunge", R.drawable.ic_lunge,
                    isCompleted = false, isSelected = false)
            exerciseList.add(lunge)
            val plank = ExerciseModel(5, "Plank", R.drawable.ic_jumping_jacks,
                    isCompleted = false, isSelected = false)
            exerciseList.add(plank)
            val pushUp = ExerciseModel(6, "Push up", R.drawable.ic_push_up,
                    isCompleted = false, isSelected = false)
            exerciseList.add(pushUp)
            val pushUpAndRotation = ExerciseModel(7, "Push up and rotation", R.drawable.ic_push_up_and_rotation,
                    isCompleted = false, isSelected = false)
            exerciseList.add(pushUpAndRotation)
            val sidePlank = ExerciseModel(8, "Side plank", R.drawable.ic_side_plank,
                    isCompleted = false, isSelected = false)
            exerciseList.add(sidePlank)
            val squat = ExerciseModel(9, "Squat", R.drawable.ic_squat,
                    isCompleted = false, isSelected = false)
            exerciseList.add(squat)
            val stepUpOntoChair = ExerciseModel(10, "Step up onto chair", R.drawable.ic_step_up_onto_chair,
                    isCompleted = false, isSelected = false)
            exerciseList.add(stepUpOntoChair)
            val tricepsDipOnChair = ExerciseModel(11, "Triceps dip on chair", R.drawable.ic_triceps_dip_on_chair,
                    isCompleted = false, isSelected = false)
            exerciseList.add(tricepsDipOnChair)
            val wallSit = ExerciseModel(12, "Wall sit", R.drawable.ic_wall_sit,
                    isCompleted = false, isSelected = false)
            exerciseList.add(wallSit)

            return exerciseList
        }
    }
}
