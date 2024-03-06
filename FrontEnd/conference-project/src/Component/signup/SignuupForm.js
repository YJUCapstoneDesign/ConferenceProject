import React from 'react';
import axios from 'axios';
import SignupButton from './SignupButton';
import { useForm } from 'react-hook-form';
import { ErrorMessage } from '@hookform/error-message';

function SignupForm() {
    const inputLabels = ['Name', 'Email', 'Password', 'Address', 'Phone'];
    const inputPlaceholders = ['Enter to Name ', 'Enter to Email', 'Enter to Password', 'Enter to Address', 'Enter to Phone Number'];

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();

    const onSubmit = async (data) => {
        try {
            const response = await axios.post('/api/signup', data);
            console.log('회원가입 성공:', response.data);
        } catch (error) {
            console.error('회원가입 실패:', error);
        }
    };

    return (
        <React.Fragment>
            <form onSubmit={handleSubmit(onSubmit)} className="my-14">
                {inputLabels.map((label, index) => (
                    <div className="mb-4" key={index}>
                        <label
                            htmlFor={`input-${index}`}
                            className="block text-gray-600 flex text-black font-bold"
                            style={{ marginBottom: '10px' }}
                        >
                            {label}
                        </label>
                        <input
                            {...register(`input-${index}`, {
                                required: label !== 'Nickname' ? "필수 사항입니다." : undefined,
                                pattern: {
                                    value: label === 'Password' ? /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/ : label === 'Phone' ? /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/ : label === 'Email' ? /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i : undefined,
                                    message: label === 'Password' ? '비밀번호 형식이 올바르지 않습니다.' : label === 'Phone' ? '전화번호 형식이 올바르지 않습니다.' : label === 'Email' ? '이메일 형식이 올바르지 않습니다.' : undefined,
                                },
                            })}
                            type={label === 'Password' ? 'password' : 'text'}
                            id={`input-${index}`}
                            name={`input-${index}`}
                            className="w-full border border-gray-300 rounded-md py-2 px-3 focus:outline-none focus:border-blue-500 rounded-xl"
                            autoComplete="off"
                            placeholder={inputPlaceholders[index]}
                            style={{backgroundColor: '#FFEBDF'}}
                        />
                        <ErrorMessage errors={errors} name={`input-${index}`} />
                    </div>
                ))}
                <SignupButton />
            </form>
        </React.Fragment>
    );
}

export default SignupForm;
