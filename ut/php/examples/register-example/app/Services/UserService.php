<?php

namespace App\Services;

use App\User;
use Illuminate\Http\UploadedFile;
use Illuminate\Support\Facades\Hash;

class UserService
{
    private function uploadAvatar(UploadedFile $file)
    {
        return $file->storePublicly('uploads', [
            'disk' => 'public',
        ]) ?: null;
    }

    public function create(array $inputs)
    {
        return User::create([
            'name' => $inputs['name'],
            'email' => $inputs['email'],
            'avatar' => $inputs['avatar'] ? $this->uploadAvatar($inputs['avatar']) : null,
            'password' => Hash::make($inputs['password']),
        ]);
    }
}
