import React from "react";

function SignupButton() {
  return (
    <React.Fragment>
      <button
        id="signup-button"
        type="submit"
        className="bg-[#000000] hover:bg-[#787878] text-white font-semibold rounded-md py-2 px-4 w-full"
      >
        Sign Up
      </button>
    </React.Fragment>
  );
}

export default SignupButton;
