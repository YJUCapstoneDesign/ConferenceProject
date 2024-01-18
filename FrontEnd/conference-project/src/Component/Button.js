import { Button } from "@material-tailwind/react";

export function ButtonRounded() {
  return (
    <div className="flex items-center gap-4">
      <Button variant="gradient" className="rounded-full bg-black text-white w-16 h-8">
        B
      </Button>
    </div>
  );
}
