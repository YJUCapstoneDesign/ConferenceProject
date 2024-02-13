import { Button } from '@material-tailwind/react';

function HomeButton() {
    return (
        <div
            className="flex justify-start rounded-xl"
            style={{
                marginTop: '1rem',
            }}
        >
            <Button
                style={{
                    padding: '12px',
                    backgroundColor: '#000080',
                    fontSize: '1rem',
                }}
            >
                요금제 및 가격
            </Button>
        </div>
    );
}

export default HomeButton;
